DESCRIPTION = "A fast and lightweight IDE"
HOMEPAGE = "http://plugins.geany.org/"

LICENSE_DEFAULT = "GPL-2.0-only"
LICENSE = "${LICENSE_DEFAULT} & BSD-2-Clause & GPL-3.0-only"

python () {
    for plugin in d.getVar('PLUGINS').split():
        if 'LICENSE:%s' % plugin not in d:
            d.setVar('LICENSE:' + plugin, '${LICENSE_DEFAULT}')
}

DEPENDS = " \
    vala-native \
    fribidi \
    geany \
    libxml2 \
    libsoup-2.4 \
    enchant2 \
    intltool-native \
    libassuan \
    gpgme \
    vte \
    libgit2 \
"

inherit features_check autotools pkgconfig gtk-icon-cache

REQUIRED_DISTRO_FEATURES = "x11"

SRC_URI = " \
    https://plugins.geany.org/${BPN}/${BP}.tar.bz2 \
    file://geany-plugins-2.0-gcc15.patch \
    file://0001-projectorganizer-fix-invalid-string-comparison.patch \
    file://0002-projectorganizer-fix-various-warnings.patch \
    file://0003-projectorganizer-Use-g_pattern_spec_match_string-ins.patch \
"
SRC_URI[sha256sum] = "9fc2ec5c99a74678fb9e8cdfbd245d3e2061a448d70fd110a6aefb62dd514705"

do_configure:prepend() {
    rm -f ${S}/build/cache/glib-gettext.m4
}

FILES:${PN} += "${datadir}/icons"
FILES:${PN}-dev += "${libdir}/geany/*.la ${libdir}/${BPN}/*/*.la"

PLUGINS += "${PN}-addons"
LIC_FILES_CHKSUM += "file://addons/COPYING;md5=4325afd396febcb659c36b49533135d4"
FILES:${PN}-addons = "${libdir}/geany/addons.so"

PLUGINS += "${PN}-autoclose"
LIC_FILES_CHKSUM += "file://autoclose/COPYING;md5=751419260aa954499f7abaabaa882bbe"
FILES:${PN}-autoclose = "${libdir}/geany/autoclose.so"

PLUGINS += "${PN}-automark"
LIC_FILES_CHKSUM += "file://automark/COPYING;md5=751419260aa954499f7abaabaa882bbe"
FILES:${PN}-automark = "${libdir}/geany/automark.so"

PLUGINS += "${PN}-codenav"
LIC_FILES_CHKSUM += "file://codenav/COPYING;md5=751419260aa954499f7abaabaa882bbe"
FILES:${PN}-codenav = "${libdir}/geany/codenav.so"

PLUGINS += "${PN}-commander"
LIC_FILES_CHKSUM += "file://commander/COPYING;md5=d32239bcb673463ab874e80d47fae504"
LICENSE:${PN}-commander = "GPL-3.0-only"
FILES:${PN}-commander = "${libdir}/geany/commander.so"

# | checking whether the GTK version in use is compatible with plugin Debugger... no
EXTRA_OECONF += "--disable-debugger"
#PLUGINS += "${PN}-debugger"
#LIC_FILES_CHKSUM += "file://debugger/COPYING;md5=4325afd396febcb659c36b49533135d4"
#FILES:${PN}-debugger = "${libdir}/geany/debugger.so ${datadir}/${PN}/debugger"

PLUGINS += "${PN}-defineformat"
LIC_FILES_CHKSUM += "file://defineformat/COPYING;md5=751419260aa954499f7abaabaa882bbe"
FILES:${PN}-defineformat = "${libdir}/geany/defineformat.so"

#PLUGINS += "${PN}-devhelp"
#LIC_FILES_CHKSUM += "file://devhelp/COPYING;md5=d32239bcb673463ab874e80d47fae504"
#LICENSE:${PN}-devhelp = "GPLv3"
#FILES:${PN}-devhelp = "${libdir}/geany/devhelp.so"

PLUGINS += "${PN}-geanyctags"
LIC_FILES_CHKSUM += "file://geanyctags/COPYING;md5=c107cf754550e65755c42985a5d4e9c9"
FILES:${PN}-geanyctags = "${libdir}/geany/geanyctags.so"

PLUGINS += "${PN}-geanydoc"
LIC_FILES_CHKSUM += "file://geanydoc/COPYING;md5=d32239bcb673463ab874e80d47fae504"
LICENSE:${PN}-geanydoc = "GPL-3.0-only"
FILES:${PN}-geanydoc = "${libdir}/geany/geanydoc.so"

PLUGINS += "${PN}-geanyextrasel"
LIC_FILES_CHKSUM += "file://geanyextrasel/COPYING;md5=c107cf754550e65755c42985a5d4e9c9"
FILES:${PN}-geanyextrasel = "${libdir}/geany/geanyextrasel.so"

PLUGINS += "${PN}-geanyinsertnum"
LIC_FILES_CHKSUM += "file://geanyinsertnum/COPYING;md5=c107cf754550e65755c42985a5d4e9c9"
FILES:${PN}-geanyinsertnum = "${libdir}/geany/geanyinsertnum.so"

# no lua: max supported version is 5.2
EXTRA_OECONF += "--disable-geanylua"
#PLUGINS += "${PN}-geanylua"
#LIC_FILES_CHKSUM += "file://geanylua/COPYING;md5=4325afd396febcb659c36b49533135d4"
#FILES:${PN}-geanylua = "${libdir}/geany/geanylua.so ${libdir}/${PN}/geanylua/*.so"

PLUGINS += "${PN}-geanymacro"
LIC_FILES_CHKSUM += "file://geanymacro/COPYING;md5=c107cf754550e65755c42985a5d4e9c9"
FILES:${PN}-geanymacro = "${libdir}/geany/geanymacro.so"

PLUGINS += "${PN}-geanyminiscript"
LIC_FILES_CHKSUM += "file://geanyminiscript/COPYING;md5=4325afd396febcb659c36b49533135d4"
FILES:${PN}-geanyminiscript = "${libdir}/geany/geanyminiscript.so"

PLUGINS += "${PN}-geanynumberedbookmarks"
LIC_FILES_CHKSUM += "file://geanynumberedbookmarks/COPYING;md5=c107cf754550e65755c42985a5d4e9c9"
FILES:${PN}-geanynumberedbookmarks = "${libdir}/geany/geanynumberedbookmarks.so"

PLUGINS += "${PN}-geanypg"
LIC_FILES_CHKSUM += "file://geanypg/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
FILES:${PN}-geanypg = "${libdir}/geany/geanypg.so"

PLUGINS += "${PN}-geanyprj"
LIC_FILES_CHKSUM += "file://geanyprj/COPYING;md5=d32239bcb673463ab874e80d47fae504"
LICENSE:${PN}-geanyprj = "GPL-3.0-only"
FILES:${PN}-geanyprj = "${libdir}/geany/geanyprj.so"

#PLUGINS += "${PN}-geanypy"
#LIC_FILES_CHKSUM += "file://geanypy/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
#FILES:${PN}-geanypy = "${libdir}/geany/geanypy.so"

PLUGINS += "${PN}-geanyvc"
LIC_FILES_CHKSUM += "file://geanyvc/COPYING;md5=c107cf754550e65755c42985a5d4e9c9"
FILES:${PN}-geanyvc = "${libdir}/geany/geanyvc.so"

PLUGINS += "${PN}-geniuspaste"
LIC_FILES_CHKSUM += "file://geniuspaste/COPYING;md5=bfc203269f8862ebfc1198cdc809a95a"
FILES:${PN}-geniuspaste = "${libdir}/geany/geniuspaste.so ${datadir}/${PN}/geniuspaste"

PLUGINS += "${PN}-git-changebar"
LIC_FILES_CHKSUM += "file://git-changebar/COPYING;md5=d32239bcb673463ab874e80d47fae504"
LICENSE:${PN}-git-changebar = "GPL-3.0-only"
FILES:${PN}-git-changebar = "${datadir}/${BPN}/git-changebar ${libdir}/geany/git-changebar.so"

PLUGINS += "${PN}-keyrecord"
LIC_FILES_CHKSUM += "file://keyrecord/COPYING;md5=751419260aa954499f7abaabaa882bbe"
FILES:${PN}-keyrecord = "${libdir}/geany/keyrecord.so"

PLUGINS += "${PN}-latex"
LIC_FILES_CHKSUM += "file://latex/COPYING;md5=c107cf754550e65755c42985a5d4e9c9"
FILES:${PN}-latex = "${libdir}/geany/latex.so"

PLUGINS += "${PN}-lineoperations"
LIC_FILES_CHKSUM += "file://lineoperations/COPYING;md5=c107cf754550e65755c42985a5d4e9c9"
FILES:${PN}-lineoperations = "${libdir}/geany/lineoperations.so"

PLUGINS += "${PN}-lipsum"
LIC_FILES_CHKSUM += "file://lipsum/COPYING;md5=4325afd396febcb659c36b49533135d4"
FILES:${PN}-lipsum = "${libdir}/geany/lipsum.so"

# no markdown - avoid floating dependencies
EXTRA_OECONF += "--disable-peg-markdown"
#PLUGINS += "${PN}-markdown"
#LIC_FILES_CHKSUM += "file://markdown/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
#FILES:${PN}-markdown = "${libdir}/geany/markdown.so"

#PLUGINS += "${PN}-multiterm"
#LIC_FILES_CHKSUM += "file://multiterm/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
#FILES:${PN}-multiterm = "${libdir}/geany/multiterm.so"

PLUGINS += "${PN}-overview"
LIC_FILES_CHKSUM += "file://overview/overview/overviewplugin.c;beginline=4;endline=20;md5=1aa33522916cdeb46cccac0c629da0d0"
FILES:${PN}-overview = "${libdir}/geany/overview.so ${datadir}/${PN}/overview"

PLUGINS += "${PN}-pairtaghighlighter"
LICENSE:${PN}-pairtaghighlighter = "BSD-2-Clause"
LIC_FILES_CHKSUM += "file://pairtaghighlighter/COPYING;md5=d6d927525a612b3a8dbebc4b2e9b47c1"
FILES:${PN}-pairtaghighlighter = "${libdir}/geany/pairtaghighlighter.so"

PLUGINS += "${PN}-pohelper"
LICENSE:${PN}-pohelper = "GPL-3.0-only"
LIC_FILES_CHKSUM += "file://pohelper/COPYING;md5=d32239bcb673463ab874e80d47fae504"
FILES:${PN}-pohelper = "${datadir}/${BPN}/pohelper ${libdir}/geany/pohelper.so"

PLUGINS += "${PN}-pretty-printer"
LIC_FILES_CHKSUM += "file://pretty-printer/src/PrettyPrinter.c;beginline=1;endline=17;md5=1665115c2fadb17c1b53cdb4e43b2440"
FILES:${PN}-pretty-printer = "${libdir}/geany/pretty-printer.so"

PLUGINS += "${PN}-projectorganizer"
LIC_FILES_CHKSUM += "file://projectorganizer/COPYING;md5=c107cf754550e65755c42985a5d4e9c9"
FILES:${PN}-projectorganizer = "${libdir}/geany/projectorganizer.so"

PLUGINS += "${PN}-scope"
LIC_FILES_CHKSUM += "file://scope/COPYING;md5=c107cf754550e65755c42985a5d4e9c9"
FILES:${PN}-scope = "${datadir}/${BPN}/scope ${libdir}/geany/scope.so"

PLUGINS += "${PN}-sendmail"
LIC_FILES_CHKSUM += "file://sendmail/COPYING;md5=c107cf754550e65755c42985a5d4e9c9"
FILES:${PN}-sendmail = "${libdir}/geany/sendmail.so"

PLUGINS += "${PN}-shiftcolumn"
LIC_FILES_CHKSUM += "file://shiftcolumn/COPYING;md5=751419260aa954499f7abaabaa882bbe"
FILES:${PN}-shiftcolumn = "${libdir}/geany/shiftcolumn.so"

PLUGINS += "${PN}-spellcheck"
LIC_FILES_CHKSUM += "file://spellcheck/COPYING;md5=4325afd396febcb659c36b49533135d4"
FILES:${PN}-spellcheck = "${libdir}/geany/spellcheck.so"

PLUGINS += "${PN}-tableconvert"
LIC_FILES_CHKSUM += "file://tableconvert/COPYING;md5=6753686878d090a1f3f9445661d3dfbc"
FILES:${PN}-tableconvert = "${libdir}/geany/tableconvert.so"

PLUGINS += "${PN}-treebrowser"
LIC_FILES_CHKSUM += "file://treebrowser/README;beginline=67;endline=67;md5=1f17f0f2abb88e0fa0f1b342112d871c"
FILES:${PN}-treebrowser = "${libdir}/geany/treebrowser.so"

PLUGINS += "${PN}-updatechecker"
LIC_FILES_CHKSUM += "file://updatechecker/COPYING;md5=4325afd396febcb659c36b49533135d4"
FILES:${PN}-updatechecker = "${libdir}/geany/updatechecker.so"

PLUGINS += "${PN}-vimode"
LIC_FILES_CHKSUM += "file://vimode/COPYING;md5=c107cf754550e65755c42985a5d4e9c9"
FILES:${PN}-vimode = "${libdir}/geany/vimode.so"

# no webkit - lasts ages and is not properly detected
EXTRA_OECONF += " --disable-webhelper"
#PLUGINS += "${PN}-webhelper"
#LIC_FILES_CHKSUM += "file://webhelper/COPYING;md5=d32239bcb673463ab874e80d47fae504"
#LICENSE:${PN}-webhelper = "GPLv3"
#FILES:${PN}-webhelper = "${libdir}/geany/webhelper.so"

PLUGINS += "${PN}-workbench"
LIC_FILES_CHKSUM += "file://workbench/COPYING;md5=c107cf754550e65755c42985a5d4e9c9"
FILES:${PN}-workbench = "${libdir}/geany/workbench.so"

PLUGINS += "${PN}-xmlsnippets"
LIC_FILES_CHKSUM += "file://xmlsnippets/COPYING;md5=4325afd396febcb659c36b49533135d4"
FILES:${PN}-xmlsnippets = "${libdir}/geany/xmlsnippets.so"

PACKAGES =+ "${PN}-common ${PLUGINS}"
FILES:${PN}-common = "${libdir}/libgeanypluginutils${SOLIBS}"

# geany-plugins is meta package for all plugins
RDEPENDS:${PN} = "${PLUGINS}"
ALLOW_EMPTY:${PN} = "1"
