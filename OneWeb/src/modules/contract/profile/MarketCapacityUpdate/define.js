export default {
  stt: {
    cho_duyet: 0,
    khong_dat: 1,
    dat: 2
  },
  textToSlug: text => {
    return text.toLowerCase()
      .replace(/ /g, '-')
      .replace(/[^\w-]+/g, '')
  }
}
