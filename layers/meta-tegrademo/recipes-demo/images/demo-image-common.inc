IMAGE_FEATURES += "ssh-server-openssh"

LICENSE = "MIT"

inherit core-image

CORE_IMAGE_BASE_INSTALL += "packagegroup-demo-base packagegroup-demo-basetests"
CORE_IMAGE_BASE_INSTALL += "${@'packagegroup-demo-systemd' if d.getVar('VIRTUAL-RUNTIME_init_manager') == 'systemd' else ''}"
CORE_IMAGE_BASE_INSTALL += "allwpilib"
CORE_IMAGE_BASE_INSTALL += "libeigen-dev"
CORE_IMAGE_BASE_INSTALL += "libuvc"
TOOLCHAIN_HOST_TASK += "nativesdk-packagegroup-cuda-sdk-host"
TOOLCHAIN_TARGET_TASK:append = " libuvc-dev libuvc-staticdev"
TOOLCHAIN_TARGET_TASK:append = " opencv-staticdev"

inherit nopackages
