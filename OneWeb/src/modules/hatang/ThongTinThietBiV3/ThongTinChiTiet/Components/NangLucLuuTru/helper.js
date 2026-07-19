export function mapNangLucResponseToCurrentItem(data) {
  return {
    thietbitud_id: data?.thietbitud_id ?? null,

    // Cache
    mem_cache: data?.dl_memcache ?? null,
    flash_cache: data?.dl_flashcache ?? null,
    raid_level: data?.raidlevel_id ?? null,

    // Trước RAID
    tong_hdd_truoc_raid: data?.dltr_hdd ?? null,
    tong_ssd_truoc_raid: data?.dltr_ssd ?? null,
    tong_nvme_truoc_raid: data?.dltr_nvme ?? null,
    // tong_iops_truoc_raid: data?.dltr_iops ?? null,

    hdd_kha_nang_cap_truoc_raid: null,
    ssd_kha_nang_cap_truoc_raid: null,
    nvme_kha_nang_cap_truoc_raid: null,

    hdd_da_cap_truoc_raid: null,
    ssd_da_cap_truoc_raid: null,
    nvme_da_cap_truoc_raid: null,

    hdd_con_trong_truoc_raid: null,
    ssd_con_trong_truoc_raid: null,
    nvme_con_trong_truoc_raid: null,

    // Sau RAID
    hdd_tong_sau_raid: data?.dlsr_hdd ?? null,
    ssd_tong_sau_raid: data?.dlsr_ssd ?? null,
    nvme_tong_sau_raid: data?.dlsr_nvme ?? null,
    iops_tong_sau_raid: data?.dl_iops ?? null,

    hdd_kha_nang_cap_sau_raid: data?.dlnc_hdd ?? null,
    ssd_kha_nang_cap_sau_raid: data?.dlnc_ssd ?? null,
    nvme_kha_nang_cap_sau_raid: data?.dlnc_nvme ?? null,
    iops_kha_nang_cap_sau_raid: data?.dlnc_iops ?? null,

    hdd_da_cap_sau_raid: data?.slsr_hdd ?? null,
    ssd_da_cap_sau_raid: data?.slsr_ssd ?? null,
    nvme_da_cap_sau_raid: data?.slsr_nvme ?? null,
    iops_da_cap_sau_raid: data?.slsr_iops ?? null,

    hdd_con_trong_sau_raid: data?.sltr_hdd ?? null,
    ssd_con_trong_sau_raid: data?.sltr_ssd ?? null,
    nvme_con_trong_sau_raid: data?.sltr_nvme ?? null,
    iops_con_trong_sau_raid: data?.sltr_iops ?? null
  }
}

