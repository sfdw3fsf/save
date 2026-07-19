export default function (apiCaller) {
  return {
    traCuuDanDoTruyenDanTheoMaFOS: (maFOS) => apiCaller.get(`web-cabman/TraCuuTruyenDan/TraCuuDanDoTruyenDanTheoMaFOS?maFOS=${maFOS}`),
  }
}
