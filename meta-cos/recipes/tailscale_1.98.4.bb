SUMMARY = "Tailscale client and daemon for Linux from Tailscale pre-built binaries"

HOMEPAGE = "github.com/tailscale/tailscale"
SECTION = "net"

LICENSE = "CLOSED"

INHIBIT_DEFAULT_DEPS = "1"

# tailscale ships pre-built binaries, so stripping and debug-split would fail
# because binutils cross tools are not in the sysroot.
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

ARCH_DIR ?= "Unsupported"
ARCH_DIR:i386 = "386"
ARCH_DIR:x86-64 = "amd64"
ARCH_DIR:aarch64 = "arm64"
ARCH_DIR:arm = "arm"

# See: https://pkgs.tailscale.com/stable/
SRC_URI = "https://pkgs.tailscale.com/stable/tailscale_1.98.4_arm64.tgz;subdir=${P};name=${ARCH_DIR}"
SRC_URI[arm64.sha256sum] = "3cb068eb1368b6bb218d0ef0aa0a7a679a7156b7c979e2279cc2c2321b5f05c7"

inherit systemd

# Pre-built binaries — no compiler needed, no stripping or debug splitting.
# Runtime dependencies, iptables required.
RDEPENDS:${PN} = "iptables"

# Runtime recommendations, kernel modules required
# RECOMMEDS as a kernel config might build them in rather than
# package as a module.
RRECOMMENDS:${PN} = "\
    kernel-module-wireguard \
    kernel-module-tun \
    kernel-module-xt-mark \
    kernel-module-xt-tcpudp \
    kernel-module-xt-masquerade"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "tailscaled.service"
SYSTEMD_AUTO_ENABLE = "enable"

# UNPACKDIR was introduced in Styhead (5.1) and its absence in previous versions (e.g. Scarthgap 5.0) leaves S
# unexpanded, causing do_unpack to fail. Conditionally setting it ensures compatibility with older versions and avoids
# interfering with Whinlatter (5.3) and later where the WORKDIR auto-move shim is removed and UNPACKDIR is mandatory.
# See: https://docs.yoctoproject.org/dev/migration-guides/migration-5.3.html#workdir-changes.
python () {
  if not d.getVar("UNPACKDIR"):
    d.setVar("UNPACKDIR", "${WORKDIR}")
}

S = "${UNPACKDIR}/${PN}-${PV}/${PN}_${PV}_${ARCH_DIR}"

do_install() {
  install -d ${D}/${bindir}
  install ${S}/tailscale ${D}/${bindir}/tailscale

  install -d ${D}/${sbindir}
  install ${S}/tailscaled ${D}/${sbindir}/tailscaled

  if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
    install -d ${D}${sysconfdir}/default/
    install -m 0644 ${S}/systemd/tailscaled.defaults ${D}${sysconfdir}/default/tailscaled

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${S}/systemd/tailscaled.service ${D}${systemd_unitdir}/system/tailscaled.service
  fi
}
