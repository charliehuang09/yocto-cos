# Keep APP at ROOTFSPART_SIZE instead of expanding it to fill the 1 TB SSD.
do_compile:append:p3768-0000-p3767-0001() {
    sed -i '/<partition name="APP" /,/<\/partition>/s/<allocation_attribute> 0x808 <\/allocation_attribute>/<allocation_attribute> 8 <\/allocation_attribute>/' ${B}/external-flash.xml
}
