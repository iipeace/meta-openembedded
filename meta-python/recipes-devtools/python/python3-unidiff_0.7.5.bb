SUMMARY = "Unified diff parsing/metadata extraction library"
HOMEPAGE = "https://github.com/matiasb/python-unidiff"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4c434b08ef42fea235bb019b5e5a97b3"

SRC_URI[sha256sum] = "2e5f0162052248946b9f0970a40e9e124236bf86c82b70821143a6fc1dea2574"

inherit pypi setuptools3 ptest-python-pytest

RDEPENDS:${PN} += " \
    python3-codecs \
    python3-io \
"
