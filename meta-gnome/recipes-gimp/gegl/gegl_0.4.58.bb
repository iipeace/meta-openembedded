SUMMARY = "GEGL (Generic Graphics Library) is a graph based image processing framework"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=f1a8bfcbc85304df454b65d378b299c7"

DEPENDS = " \
    intltool-native \
    babl \
    glib-2.0 \
    cairo \
    expat \
    zlib \
    \
    json-glib \
    libpng \
    jpeg \
"

DEPENDS:append:toolchain-clang  = " openmp"
DEPENDS:remove:toolchain-clang:riscv32  = "openmp"
DEPENDS:remove:toolchain-clang:powerpc  = "openmp"

VALA_MESON_OPTION = ""

inherit features_check gnomebase gobject-introspection vala

ANY_OF_DISTRO_FEATURES = "${GTK3DISTROFEATURES}"

SHPV = "${@gnome_verdir("${PV}")}"

SRC_URI = "https://download.gimp.org/pub/${BPN}/${SHPV}/${BP}.tar.xz"
SRC_URI[sha256sum] = "d5678bbd5fe535941b82f965b97fcc9385ce936f70c982bd565a53d5519d1bff"

PACKAGECONFIG ??= "gexiv2 libraw librsvg pango poppler sdl2"
PACKAGECONFIG:class-native = "librsvg"

PACKAGECONFIG[jasper] = "-Djasper=enabled,-Djasper=disabled,jasper"
PACKAGECONFIG[gexiv2] = "-Dgexiv2=enabled,-Dgexiv2=disabled,gexiv2"
PACKAGECONFIG[graphviz] = "-Dgraphviz=enabled,-Dgraphviz=disabled,graphviz"
PACKAGECONFIG[lcms] = "-Dlcms=enabled,-Dlcms=disabled,lcms"
PACKAGECONFIG[libav] = "-Dlibav=enabled,-Dlibav=disabled,libav"
PACKAGECONFIG[libraw] = "-Dlibraw=enabled,-Dlibraw=disabled,libraw"
PACKAGECONFIG[librsvg] = "-Dlibrsvg=enabled,-Dlibrsvg=disabled,librsvg"
PACKAGECONFIG[pango] = "-Dpango=enabled -Dpangocairo=enabled,-Dpango=disabled -Dpangocairo=disabled,pango"
PACKAGECONFIG[poppler] = "-Dpoppler=enabled,-Dpoppler=disabled,poppler"
PACKAGECONFIG[sdl] = "-Dsdl1=enabled,-Dsdl1=disabled,libsdl"
PACKAGECONFIG[sdl2] = "-Dsdl2=enabled,-Dsdl2=disabled,virtual/libsdl2"
PACKAGECONFIG[tiff] = "-Dlibtiff=enabled,-Dlibtiff=disabled,tiff"
PACKAGECONFIG[webp] = "-Dwebp=enabled,-Dwebp=disabled,libwebp"

# There are a couple of non-symlink .so files installed into libdir, which need to go into main package
FILES:${PN} += " \
    ${libdir}/*.so \
    ${libdir}/gegl-${SHPV}/*.json \
    ${libdir}/gegl-${SHPV}/*.so \
"
FILES_SOLIBSDEV = "${libdir}/libgegl-${SHPV}${SOLIBSDEV}"

# Fails to build with thumb-1 (qemuarm)
# gegl-0.2.0/operations/common/matting-global.c: In function 'matting_process':
# gegl-0.2.0/operations/common/matting-global.c:463:1: internal compiler error: in patch_jump_insn, at cfgrtl.c:1275
ARM_INSTRUCTION_SET = "arm"

BBCLASSEXTEND = "native"
