#!/usr/bin/env python3
"""
Element Data Scraper
Fetches and merges element data from multiple sources into a comprehensive JSON file.

Sources:
1. Bowserinator/Periodic-Table-JSON - Basic data, images, 3D models
2. komed3/periodic-table - Advanced data, nuclides, spectral data
"""

import json
import re
import urllib.request
from datetime import datetime
from typing import Any, Dict, List, Optional

# Data source URLs
BOWSERINATOR_URL = "https://raw.githubusercontent.com/Bowserinator/Periodic-Table-JSON/master/PeriodicTableJSON.json"
KOMED3_ELEMENTS_URL = "https://raw.githubusercontent.com/komed3/periodic-table/main/_db/elements.json"
KOMED3_NUCLIDES_URL = "https://raw.githubusercontent.com/komed3/periodic-table/main/_db/nuclides.json"

# Category mapping from Bowserinator categories to our enum values
CATEGORY_MAP = {
    "diatomic nonmetal": "NONMETAL",
    "polyatomic nonmetal": "NONMETAL",
    "nonmetal": "NONMETAL",
    "alkali metal": "ALKALI_METAL",
    "alkaline earth metal": "ALKALINE_EARTH_METAL",
    "transition metal": "TRANSITION_METAL",
    "post-transition metal": "POST_TRANSITION_METAL",
    "metalloid": "METALLOID",
    "halogen": "HALOGEN",
    "noble gas": "NOBLE_GAS",
    "lanthanide": "LANTHANIDE",
    "actinide": "ACTINIDE",
    "unknown, probably transition metal": "TRANSITION_METAL",
    "unknown, probably post-transition metal": "POST_TRANSITION_METAL",
    "unknown, probably metalloid": "METALLOID",
    "unknown, predicted to be noble gas": "NOBLE_GAS",
    "unknown": "UNKNOWN",
}

# Phase mapping
PHASE_MAP = {
    "Gas": "GAS",
    "Liquid": "LIQUID",
    "Solid": "SOLID",
    "Unknown": "UNKNOWN",
}


def fetch_json(url: str) -> Any:
    """Fetch JSON from URL."""
    print(f"Fetching: {url}")
    with urllib.request.urlopen(url) as response:
        return json.loads(response.read().decode())


def safe_get(obj: Any, *keys: str, default: Any = None) -> Any:
    """Safely get nested dictionary values."""
    for key in keys:
        if obj is None:
            return default
        if isinstance(obj, dict):
            obj = obj.get(key)
        else:
            return default
    return obj if obj is not None else default


def extract_value(obj: Any, default: Any = None) -> Any:
    """Extract value from nested structures.

    Handles:
    - None -> default
    - Simple values (int, float, str) -> value
    - Dict with "value" key -> value
    - List of dicts -> first element's value
    """
    if obj is None:
        return default
    if isinstance(obj, (int, float, str, bool)):
        return obj
    if isinstance(obj, list):
        if len(obj) > 0:
            first = obj[0]
            if isinstance(first, dict):
                return first.get("value", default)
            return first
        return default
    if isinstance(obj, dict):
        return obj.get("value", default)
    return obj


def parse_oxidation_states(ox_string: str) -> List[int]:
    """Parse oxidation states from string like '+1, 0, -1' or '1, 2, 3'."""
    if not ox_string:
        return []
    states = []
    for part in ox_string.replace(" ", "").split(","):
        try:
            states.append(int(part))
        except ValueError:
            pass
    return states


def extract_first_classification(data: Any, extract_int: bool = False) -> Any:
    """Extract first value from classification arrays like CAS, CID, RTECS.

    These come in format: [{"value": "1333-74-0", "label": "H"}] or [{"value": "CID783"}]
    """
    if data is None:
        return None
    if isinstance(data, list) and len(data) > 0:
        first = data[0]
        if isinstance(first, dict):
            value = first.get("value")
            if value and extract_int:
                # Extract numeric part from strings like "CID783"
                import re
                match = re.search(r'\d+', str(value))
                return int(match.group()) if match else None
            return value
    return data if not isinstance(data, (list, dict)) else None


def get_isotopes_for_element(nuclides_data: Dict, symbol: str, atomic_number: int) -> List[Dict]:
    """Extract isotopes for a specific element from nuclides data."""
    isotopes = []
    symbol_lower = symbol.lower()

    if symbol_lower not in nuclides_data:
        return isotopes

    element_data = nuclides_data[symbol_lower]

    # Get the nuclides list
    nuclides_list = element_data.get("nuclides", [])
    if not isinstance(nuclides_list, list):
        return isotopes

    for nuclide in nuclides_list:
        mass_number = nuclide.get("m")
        if mass_number is None:
            continue

        # Parse atomic mass (in keV/c^2, convert to u)
        atomic_mass_data = nuclide.get("atomic_mass", {})
        atomic_mass_kev = atomic_mass_data.get("value") if isinstance(atomic_mass_data, dict) else None
        # Convert keV/c^2 to atomic mass units (1 u = 931494.0954 keV/c^2)
        atomic_mass = atomic_mass_kev / 931494.0954 if atomic_mass_kev else None

        # Parse abundance
        abundance_data = nuclide.get("abundance", {})
        abundance = abundance_data.get("value", 0) if isinstance(abundance_data, dict) else 0

        # Parse binding energy
        binding_data = nuclide.get("binding", {})
        binding_energy = binding_data.get("value") if isinstance(binding_data, dict) else None

        # Parse magnetic dipole
        magnetic_data = nuclide.get("magnetic_dipole", {})
        magnetic_dipole = magnetic_data.get("value") if isinstance(magnetic_data, dict) else None

        isotope = {
            "massNumber": mass_number,
            "neutrons": nuclide.get("n", mass_number - atomic_number),
            "atomicMass": atomic_mass,
            "abundance": abundance,
            "halfLife": None,
            "halfLifeUnit": None,
            "decayModes": [],
            "spin": nuclide.get("jp"),
            "stable": nuclide.get("stable", False),
            "bindingEnergy": binding_energy,
            "magneticDipole": magnetic_dipole,
        }

        # Parse half-life (always as string for consistency - can be numeric or "?")
        half_life_data = nuclide.get("half_life")
        if half_life_data and isinstance(half_life_data, dict):
            half_life_value = half_life_data.get("value")
            isotope["halfLife"] = str(half_life_value) if half_life_value is not None else None
            isotope["halfLifeUnit"] = half_life_data.get("unit")

        # Parse decay modes
        decay_data = nuclide.get("decay", [])
        if isinstance(decay_data, list):
            for decay in decay_data:
                if isinstance(decay, dict):
                    mode = decay.get("mode")
                    if mode:
                        isotope["decayModes"].append(mode)

        isotopes.append(isotope)

    # Sort by mass number
    isotopes.sort(key=lambda x: x["massNumber"])

    return isotopes


def merge_element_data(bowser_el: Dict, komed3_data: Dict, nuclides_data: Dict, atomic_number: int) -> Dict:
    """Merge data from both sources for a single element."""
    symbol = bowser_el.get("symbol", "")
    symbol_lower = symbol.lower()

    # Get komed3 element data
    komed3_el = komed3_data.get(symbol_lower, {})

    # Map category
    category = bowser_el.get("category", "unknown")
    mapped_category = CATEGORY_MAP.get(category.lower(), "UNKNOWN")

    # Map phase
    phase = bowser_el.get("phase", "Unknown")
    mapped_phase = PHASE_MAP.get(phase, "UNKNOWN")

    # Get image data
    image_data = bowser_el.get("image", {})
    image_url = image_data.get("url") if isinstance(image_data, dict) else None
    image_title = image_data.get("title") if isinstance(image_data, dict) else None
    image_attribution = image_data.get("attribution") if isinstance(image_data, dict) else None

    # Get isotopes
    isotopes = get_isotopes_for_element(nuclides_data, symbol, atomic_number)

    # Build merged element - extract values from nested objects
    element = {
        # === IDENTIFICATION ===
        "atomicNumber": atomic_number,
        "symbol": symbol,
        "name": bowser_el.get("name", ""),
        "latinName": extract_value(safe_get(komed3_el, "names", "la")),

        # === ATOMIC PROPERTIES ===
        "atomicMass": bowser_el.get("atomic_mass"),
        "standardAtomicWeight": None,

        # === CLASSIFICATION ===
        "category": mapped_category,
        "group": bowser_el.get("group"),
        "period": bowser_el.get("period"),
        "block": bowser_el.get("block", "s"),

        # === ELECTRON STRUCTURE ===
        "electronConfiguration": bowser_el.get("electron_configuration", ""),
        "electronConfigurationSemantic": bowser_el.get("electron_configuration_semantic", ""),
        "electronShells": bowser_el.get("shells", []),

        # === ELECTRONEGATIVITY (Multiple Scales) - Extract values ===
        "electronegativity": bowser_el.get("electronegativity_pauling"),
        "electronegativityPauling": bowser_el.get("electronegativity_pauling"),
        "electronegativitySanderson": extract_value(safe_get(komed3_el, "negativity", "sanderson")),
        "electronegativityAllredRochow": extract_value(safe_get(komed3_el, "negativity", "allred-rochow")),
        "electronegativityMulliken": extract_value(safe_get(komed3_el, "negativity", "mulliken")),
        "electronegativityAllen": extract_value(safe_get(komed3_el, "negativity", "allen")),

        # === IONIZATION ENERGIES ===
        "ionizationEnergies": bowser_el.get("ionization_energies", []),
        "electronAffinity": bowser_el.get("electron_affinity"),

        # === ATOMIC RADII (Multiple Types) - Extract values in pm ===
        "atomicRadiusEmpirical": extract_value(safe_get(komed3_el, "radius", "empirical")),
        "atomicRadiusCalculated": extract_value(safe_get(komed3_el, "radius", "calculated")),
        "covalentRadius": extract_value(safe_get(komed3_el, "radius", "covalent")),
        "vanDerWaalsRadius": extract_value(safe_get(komed3_el, "radius", "vanderwaals")),

        # === PHYSICAL PROPERTIES ===
        "naturalState": mapped_phase,
        "density": bowser_el.get("density"),
        "meltingPoint": bowser_el.get("melt"),
        "boilingPoint": bowser_el.get("boil"),
        "heatOfFusion": extract_value(safe_get(komed3_el, "enthalpy", "fusion")),
        "heatOfVaporization": extract_value(safe_get(komed3_el, "enthalpy", "vaporisation")),
        "molarHeatCapacity": bowser_el.get("molar_heat"),
        "specificHeatCapacity": extract_value(safe_get(komed3_el, "heat", "capacity")),

        # === APPEARANCE ===
        # Prefer Bowserinator, fallback to komed3 English appearance
        "appearance": bowser_el.get("appearance") or safe_get(komed3_el, "appearance", "en"),

        # === THERMAL PROPERTIES ===
        "thermalConductivity": extract_value(safe_get(komed3_el, "heat", "conductivity")),
        "thermalExpansion": extract_value(safe_get(komed3_el, "heat", "expansion")),

        # === ELECTRICAL PROPERTIES ===
        "electricalConductivity": None,
        "superconductingPoint": None,

        # === MAGNETIC PROPERTIES ===
        "magneticOrdering": extract_value(safe_get(komed3_el, "magnetic_ordering")),
        "magneticSusceptibility": extract_value(safe_get(komed3_el, "magnetic_susceptibility")),

        # === MECHANICAL PROPERTIES ===
        "mohsHardness": None,
        "youngsModulus": None,
        "shearModulus": None,
        "bulkModulus": None,
        "poissonRatio": None,
        "soundSpeed": extract_value(safe_get(komed3_el, "sound_speed")),

        # === OPTICAL PROPERTIES ===
        "refractiveIndex": extract_value(safe_get(komed3_el, "optical", "solid")),

        # === CRYSTAL STRUCTURE ===
        "crystalStructure": extract_value(safe_get(komed3_el, "crystal_structure")),

        # === CHEMICAL PROPERTIES ===
        "oxidationStates": parse_oxidation_states(safe_get(komed3_el, "oxidation_state", default="")),
        "standardElectrodePotential": extract_value(safe_get(komed3_el, "standard_potential")),
        "basicity": extract_value(safe_get(komed3_el, "basicity")),

        # === DISCOVERY ===
        "discoveredBy": bowser_el.get("discovered_by"),
        "namedBy": bowser_el.get("named_by"),
        "discoveryYear": extract_value(safe_get(komed3_el, "discovery", "year")),
        "era": komed3_el.get("era"),  # Historical period (e.g., "antiquity", "1800s")
        "nameOrigin": None,

        # === ABUNDANCE (ppb) - Extract values ===
        "abundanceUniverse": extract_value(safe_get(komed3_el, "abundance", "universe")),
        "abundanceSun": extract_value(safe_get(komed3_el, "abundance", "sun")),
        "abundanceMeteor": extract_value(safe_get(komed3_el, "abundance", "meteorite")),
        "abundanceCrust": extract_value(safe_get(komed3_el, "abundance", "crust")),
        "abundanceOcean": extract_value(safe_get(komed3_el, "abundance", "water")),
        "abundanceHuman": extract_value(safe_get(komed3_el, "abundance", "human")),

        # === CLASSIFICATION IDS ===
        "casNumber": extract_first_classification(safe_get(komed3_el, "classification", "cas")),
        "pubchemCid": extract_first_classification(safe_get(komed3_el, "classification", "cid"), extract_int=True),
        "rtecsNumber": extract_first_classification(safe_get(komed3_el, "classification", "rtecs")),

        # === HAZARD DATA ===
        "ghsSymbols": safe_get(komed3_el, "hazard", "ghs", default=[]),
        "hStatements": safe_get(komed3_el, "hazard", "h", default=[]),
        "pStatements": safe_get(komed3_el, "hazard", "p", default=[]),
        "nfpaHealth": safe_get(komed3_el, "hazard", "nfpa", "health"),
        "nfpaFire": safe_get(komed3_el, "hazard", "nfpa", "fire"),
        "nfpaReactivity": safe_get(komed3_el, "hazard", "nfpa", "reactivity"),

        # === PRICE ===
        "pricePerKg": extract_value(safe_get(komed3_el, "price")),

        # === IMAGES & 3D MODELS ===
        "imageUrl": image_url,
        "imageTitle": image_title,
        "imageAttribution": image_attribution,
        "bohrModelImage": bowser_el.get("bohr_model_image"),
        "bohrModel3d": bowser_el.get("bohr_model_3d"),
        "spectralImage": bowser_el.get("spectral_img"),

        # === TEXT CONTENT ===
        "summary": bowser_el.get("summary"),
        "wikipediaUrl": bowser_el.get("source"),

        # === CPK COLOR ===
        "cpkHexColor": bowser_el.get("cpk-hex"),

        # === ISOTOPES ===
        "isotopes": isotopes,

        # === LEGACY FIELDS (for backward compatibility) ===
        "trivia": [bowser_el.get("summary")] if bowser_el.get("summary") else [],
        "realWorldUses": [],
        "whereFound": [],
    }

    return element


def main():
    """Main entry point."""
    print("=" * 60)
    print("Element Data Scraper")
    print("=" * 60)

    # Fetch all data sources
    print("\n[1/3] Fetching Bowserinator data...")
    bowser_data = fetch_json(BOWSERINATOR_URL)

    print("[2/3] Fetching komed3 elements data...")
    komed3_data = fetch_json(KOMED3_ELEMENTS_URL)

    print("[3/3] Fetching komed3 nuclides data...")
    nuclides_data = fetch_json(KOMED3_NUCLIDES_URL)

    # Process elements
    print("\nProcessing 118 elements...")
    elements = []

    for bowser_el in bowser_data.get("elements", []):
        atomic_number = bowser_el.get("number")
        if atomic_number is None:
            continue

        element = merge_element_data(bowser_el, komed3_data, nuclides_data, atomic_number)
        elements.append(element)

        # Progress indicator
        if atomic_number % 10 == 0:
            print(f"  Processed {atomic_number}/118 elements...")

    # Sort by atomic number
    elements.sort(key=lambda x: x["atomicNumber"])

    # Build final output
    output = {
        "version": 2,
        "generated": datetime.utcnow().isoformat() + "Z",
        "sources": [
            "Bowserinator/Periodic-Table-JSON",
            "komed3/periodic-table"
        ],
        "elements": elements
    }

    # Write output
    output_path = "../content/periodic-table/src/main/assets/elements.json"
    print(f"\nWriting to {output_path}...")

    with open(output_path, "w", encoding="utf-8") as f:
        json.dump(output, f, indent=2, ensure_ascii=False)

    # Statistics
    print("\n" + "=" * 60)
    print("COMPLETE!")
    print("=" * 60)
    print(f"Total elements: {len(elements)}")

    # Count elements with various data
    with_images = sum(1 for e in elements if e.get("imageUrl"))
    with_3d = sum(1 for e in elements if e.get("bohrModel3d"))
    with_isotopes = sum(1 for e in elements if e.get("isotopes"))
    total_isotopes = sum(len(e.get("isotopes", [])) for e in elements)

    print(f"Elements with images: {with_images}")
    print(f"Elements with 3D models: {with_3d}")
    print(f"Elements with isotopes: {with_isotopes}")
    print(f"Total isotopes: {total_isotopes}")


if __name__ == "__main__":
    main()
