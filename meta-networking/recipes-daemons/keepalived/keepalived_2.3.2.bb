SUMMARY = "High Availability monitor built upon LVS, VRRP and service pollers"
DESCRIPTION = "Keepalived is a routing software written in C. The main goal \
of this project is to provide simple and robust facilities for loadbalancing \
and high-availability to Linux system and Linux based infrastructures. \
Loadbalancing framework relies on well-known and widely used Linux Virtual \
Server (IPVS) kernel module providing Layer4 loadbalancing \
"
HOMEPAGE = "http://www.keepalived.org/"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "http://www.keepalived.org/software/${BP}.tar.gz \
           file://0001-configure.ac-Do-not-emit-compiler-flags-into-object-.patch \
           file://0001-vrrp-Don-t-include-linux-if_ether.h-if-not-needed.patch \
           "
SRC_URI[sha256sum] = "77f4a22e5a23fa8e49b8916acdfb584c864e72905a2f1de2a7f62ed40a896160"
UPSTREAM_CHECK_URI = "https://github.com/acassen/keepalived/releases"

DEPENDS = "libnfnetlink openssl"

inherit autotools pkgconfig systemd

PACKAGECONFIG ??= "libnl snmp reproducible \
    ${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)} \
"
PACKAGECONFIG[libnl] = "--enable-libnl,--disable-libnl,libnl"
PACKAGECONFIG[reproducible] = "--enable-reproducible-build,,"
PACKAGECONFIG[snmp] = "--enable-snmp,--disable-snmp,net-snmp"
PACKAGECONFIG[systemd] = "--with-init=systemd --with-systemdsystemunitdir=${systemd_system_unitdir},--with-init=SYSV,systemd"

EXTRA_OEMAKE = "initdir=${sysconfdir}/init.d"

export EXTRA_CFLAGS = "${CFLAGS}"

do_configure:append() {
    sed -i -e 's|${WORKDIR}|<scrubbed>|g' ${B}/lib/config.h
}

do_install:append() {
    if [ -f ${D}${sysconfdir}/init.d/${BPN} ]; then
        chmod 0755 ${D}${sysconfdir}/init.d/${BPN}
        sed -i 's#rc.d/##' ${D}${sysconfdir}/init.d/${BPN}
    fi

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -D -m 0644 ${B}/${BPN}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service
    fi
    if [ -n "${@bb.utils.filter('PACKAGECONFIG', 'reproducible', d)}" ]; then
        sed -i -e 's|${WORKDIR}|<scrubbed>|g' ${D}${sysconfdir}/keepalived/keepalived.config-opts
    fi
}

PACKAGE_BEFORE_PN = "${PN}-samples"

FILES:${PN} += "${datadir}/snmp/mibs/KEEPALIVED-MIB.txt"

FILES:${PN}-samples = "${sysconfdir}/keepalived/samples ${sysconfdir}/keepalived/keepalived.conf.sample"

SYSTEMD_SERVICE:${PN} = "keepalived.service"
SYSTEMD_AUTO_ENABLE ?= "disable"
