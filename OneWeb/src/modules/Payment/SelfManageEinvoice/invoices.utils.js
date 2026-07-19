function getInt(char) {
    return parseInt(char, 10);
  }
  
  export function checkMST(mST) {
    let isTrue = false;
  
    if (mST.length !== 14 && mST.length !== 10) return false;
    if (mST.length === 14 && mST[10] !== '-') return false;
    const index = mST.length;
    if (!isNumber(mST[index - 1]) || !isNumber(mST[index - 2]) || !isNumber(mST[index - 3])) return false;
  
    const value = getInt(mST[0]) * 31 +
                  getInt(mST[1]) * 29 +
                  getInt(mST[2]) * 23 +
                  getInt(mST[3]) * 19 +
                  getInt(mST[4]) * 17 +
                  getInt(mST[5]) * 13 +
                  getInt(mST[6]) * 7 +
                  getInt(mST[7]) * 5 +
                  getInt(mST[8]) * 3;
  
    const mod = 10 - (value % 11);
    if (Math.abs(mod) === getInt(mST[9])) {
      isTrue = true;
    }
    debugger
    return isTrue;
  }
  
  function isNumber(char) {
    return !isNaN(char) && char.trim() !== '';
  }