export function getPositiveNumber(value) {
  const number = parseFloat(value)

  if (isNaN(number)) {
    return 0
  }
  return Math.abs(number)
}
