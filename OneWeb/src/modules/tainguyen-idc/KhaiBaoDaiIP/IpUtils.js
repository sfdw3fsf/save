const IpUtils = {
  // Chuyển IP string -> số nguyên
  ipToInt(ip) {
    return ip
        .split('.')
        .reduce((acc, octet) => (acc << 8) + parseInt(octet, 10), 0) >>> 0;
  },
  // Chuyển số nguyên -> IP string
  intToIp(int) {
    return [
      (int >>> 24) & 255,
      (int >>> 16) & 255,
      (int >>> 8) & 255,
      int & 255
    ].join(".");
  },
  maskToInt(mask) {
    return mask
        .trim()
        .split('.')
        .reduce((acc, octet) => (acc << 8) + parseInt(octet, 10), 0) >>> 0;
  },
  // Check IPv4 hợp lệ
  isIPv4(ip) {
    if (!ip || typeof ip !== "string") return false;
    const ipv4Regex =
        /^(25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)$/;
    return ipv4Regex.test(ip.trim());
  },
  isIPv4withMask(ip, subnetMask) {
    if (typeof ip !== "string" || typeof subnetMask !== "string") return false;

    // check format
    const ipParts = ip.split(".");
    const maskParts = subnetMask.split(".");
    if (ipParts.length !== 4 || maskParts.length !== 4) return false;

    for (let i = 0; i < 4; i++) {
      const ipNum = Number(ipParts[i]);
      const maskNum = Number(maskParts[i]);
      if (!/^\d+$/.test(ipParts[i]) || !/^\d+$/.test(maskParts[i])) return false;
      if (ipNum < 0 || ipNum > 255 || maskNum < 0 || maskNum > 255) return false;
    }

    const ipInt = this.ipToInt(ip);
    const maskInt = this.maskToInt(subnetMask);
    const network = ipInt & maskInt;
    const broadcast = network | (~maskInt >>> 0);

    // loại bỏ network và broadcast
    if (ipInt === network || ipInt === broadcast) {
      return false;
    }

    return true;
  },
  checkGatewayValid(ipGateway, subnet, subnetMask) {
    try {
      const ip = this.ipToInt(ipGateway);
      const anyIpInSubnet = this.ipToInt(subnet);
      const mask = this.maskToInt(subnetMask);

      // Network & broadcast tính từ subnet bất kỳ
      const network = anyIpInSubnet & mask;
      const broadcast = network | (~mask >>> 0);

      // Nếu gateway nằm trong cùng subnet
      if ((ip & mask) !== network) return false;

      // Gateway không được là network hoặc broadcast
      if (ip === network || ip === broadcast) return false;

      return true;
    } catch (e) {
      return false;
    }
  },
  maskToPrefix(mask) {
    return mask.split('.')
      .map(octet => parseInt(octet, 10)
        .toString(2).padStart(8, '0'))
      .join('')
      .split('1').length - 1;
  },

  // Subnet mask -> prefix length (vd: 255.255.255.0 -> 24)
  subnetMaskToPrefix(mask) {
    return mask.split('.')
      .map(octet => parseInt(octet, 10)
        .toString(2)
        .padStart(8, '0'))
      .join('')
      .replace(/0+$/, '').length;
  },

  // CIDR -> range {start, end}
  cidrToRange(cidr) {
    const [ip, prefix] = cidr.split("/");
    const ipInt = this.ipToInt(ip);
    const mask = prefix == 0 ? 0 : (0xFFFFFFFF << (32 - prefix)) >>> 0;
    const network = ipInt & mask;
    const broadcast = network | (~mask >>> 0);
    return {start: network, end: broadcast};
  },

  // Check IP có nằm trong subnet
  // isIpInSubnet(ip, networkIp, subnetMask) {
  //     if (!this.isIPv4(ip, subnetMask) || !this.isIPv4(networkIp, subnetMask) || !this.isIPv4(subnetMask)) {
  //         return false;
  //     }
  //     const prefix = this.subnetMaskToPrefix(subnetMask);
  //     const cidr = `${networkIp}/${prefix}`;
  //     const range = this.cidrToRange(cidr);
  //     const ipInt = this.ipToInt(ip);
  //     return ipInt >= range.start && ipInt <= range.end;
  // },
  /**
   * Kiểm tra dải con có thuộc dải cha không
   */
  isSubnetOf(childCidr, parentCidr) {
    const child = this.cidrToRange(childCidr);
    const parent = this.cidrToRange(parentCidr);
    return child.start >= parent.start && child.end <= parent.end;
  },
  sameSubnet(ip1, ip2, mask) {
    if (!this.isIPv4withMask(ip1, mask) || !this.isIPv4withMask(ip2, mask)) return false;

    const ipInt1 = this.ipToInt(ip1);
    const ipInt2 = this.ipToInt(ip2);
    const maskInt = this.ipToInt(mask);
    return (ipInt1 & maskInt) === (ipInt2 & maskInt);
  },
  isIpRangeValid(ipStart, ipEnd, subnetMask) {
    try {
      const start = this.ipToInt(ipStart.trim());
      const end = this.ipToInt(ipEnd.trim());
      const mask = this.maskToInt(subnetMask.trim());
      // ✅ Tính network và broadcast chuẩn
      const network = (start & mask) >>> 0;
      const broadcast = (network + (~mask >>> 0)) >>> 0;
      // 1️⃣ Cùng subnet
      if (((end & mask) >>> 0) !== (network >>> 0)) return false;
      // 2️⃣ Thứ tự hợp lệ
      if (start > end) return false;
      // 3️⃣ Trong dải subnet
      if (start < network || end > broadcast) return false;
      // 4️⃣ Không phải network hay broadcast
      if (start === network || end === broadcast) return false;
      return true;
    } catch (e) {
      console.error("Error:", e);
      return false;
    }
  },
  isIpInRange(ip, ipRange, subnetMask) {
    try {
      const ipInt = this.ipToInt(ip);
      const rangeInt = this.ipToInt(ipRange);

      // Nếu ipRange là 1 IP cụ thể (không phải network)
      if (subnetMask === undefined || subnetMask === null) {
        return ipInt === rangeInt;
      }

      // Nếu có subnet mask: ipRange được coi là 1 IP trong subnet
      const mask = this.maskToInt(subnetMask);

      const network = rangeInt & mask;
      const broadcast = network | (~mask >>> 0);

      // ip phải nằm trong cùng subnet
      if ((ipInt & mask) !== network) return false;

      // không cho phép network và broadcast
      if (ipInt === network || ipInt === broadcast) return false;

      return true;
    } catch (e) {
      return false;
    }
  },
  isSubnetOfUseMask(ipChild, maskChild, ipParent, maskParent) {
    try {
      const ipC = this.ipToInt(ipChild);
      const ipP = this.ipToInt(ipParent);
      const maskC = this.maskToInt(maskChild);
      const maskP = this.maskToInt(maskParent);

      const netC = ipC & maskC;
      const netP = ipP & maskP;

      // Con phải nằm trong mạng cha
      if ((netC & maskP) !== netP) return false;

      // Tính prefix
      const prefixC = this.maskToPrefix(maskChild);
      const prefixP = this.maskToPrefix(maskParent);

      // Cha phải rộng hơn hoặc bằng
      if (prefixP <= prefixC) return true;

      return false;
    } catch (e) {
      return false;
    }
  },
  // Check subnet con có thuộc subnet cha
  // isSubnetOfUseMask(childIp, childMask, parentIp, parentMask) {
  //     if (!this.isIPv4(childIp) || !this.isIPv4(parentIp) || !this.isIPv4(childMask) || !this.isIPv4(parentMask)) {
  //         return false;
  //     }
  //
  //     const childPrefix = this.subnetMaskToPrefix(childMask);
  //     const parentPrefix = this.subnetMaskToPrefix(parentMask);
  //
  //     const childCidr = `${childIp}/${childPrefix}`;
  //     const parentCidr = `${parentIp}/${parentPrefix}`;
  //
  //     const child = this.cidrToRange(childCidr);
  //     const parent = this.cidrToRange(parentCidr);
  //
  //     return child.start >= parent.start && child.end <= parent.end;
  // },
  isValidNetworkAddress(ip) {
    if (!this.isIPv4(ip)) return false;

    const parts = ip.split('.').map(Number);
    if (parts[3] !== 0) return false; // phải kết thúc bằng 0

    return true;
  },
  isValidNetworkAddressWithMask(ip, subnetMask) {
    if (!this.isValidNetworkAddress(ip)) return false;

    const ipInt = this.ipToInt(ip);
    const maskInt = this.ipToInt(subnetMask);
    const network = (ipInt & maskInt) >>> 0;
    return ipInt === network;
  },
  validateSubnetFormat: function (subnet) {
    if (!subnet) return false;

    // IPv4 validation (e.g., 192.168.1.0)
    const ipv4Regex = /^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

    // IPv6 validation (e.g., 2001:db8::1)
    const ipv6Regex = /^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$|^([0-9a-fA-F]{1,4}:){1,7}:$|^([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}$|^([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}$|^([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}$|^([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}$|^([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}$|^[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})$|^:((:[0-9a-fA-F]{1,4}){1,7}|:)$/;

    // IPv6 compressed format validation (e.g., ::1, 2001:db8::)
    const ipv6CompressedRegex = /^::$|^::1$|^([0-9a-fA-F]{1,4}::)|^(([0-9a-fA-F]{1,4}:){1,6}:)$/;

    return ipv4Regex.test(subnet) || ipv6Regex.test(subnet) || ipv6CompressedRegex.test(subnet);
  },
  /**
   * Kiểm tra IP có nằm trong dải không
   isIpInCidr(ip, cidr) {
   if (!this.isIPv4(ip)) return false;
   const ipInt = this.ipToInt(ip);
   const range = this.cidrToRange(cidr);
   return ipInt >= range.start && ipInt <= range.end;
   },
   */
  // ---- IPv6 helpers ----
  // normalizeIPv6(ip) {
  //     if (!ip.includes("::")) {
  //         return ip.split(":").map(x => x.padStart(4, "0")).join(":");
  //     }
  //     const parts = ip.split("::");
  //     const left = parts[0] ? parts[0].split(":") : [];
  //     const right = parts[1] ? parts[1].split(":") : [];
  //     const missing = 8 - (left.length + right.length);
  //     const middle = Array(missing).fill("0");
  //     return [...left, ...middle, ...right].map(x => x.padStart(4, "0")).join(":");
  // },

  // ip6ToBigInt(ip) {
  //     const norm = this.normalizeIPv6(ip);
  //     return norm.split(":").reduce(
  //         (acc, block) => (acc << 16n) + BigInt(parseInt(block, 16)),
  //         0n
  //     );
  // },

  // isIPv6(ip) {
  //     try {
  //         this.ip6ToBigInt(ip);
  //         return true;
  //     } catch {
  //         return false;
  //     }
  // },
};

export default IpUtils;
