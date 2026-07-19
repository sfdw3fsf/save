import { Global } from '@emotion/react';

const Fonts = () => {
  return (
    <Global
      styles={`
       @font-face {
        font-family: 'Pretendard-Bold';
        font-style: normal;
        font-weight: 700;
        font-display: swap;
        src: url('./fonts/pretendard-bold.woff') format('woff2');
        unicode-range: U+20, U+21, U+23, U+26-29, U+2C-33, U+35-38, U+3A, U+3F, U+41-57, U+61-7A, U+A9, U+E9, U+2013, U+2014, U+2026, U+2AF6, U+1F44B;
      }
      @font-face {
        font-family: 'Pretendard-Semibold';
        font-style: normal;
        font-weight: 600;
        font-display: swap;
        src: url('./fonts/pretendard-semibold.woff') format('woff2');
        unicode-range: U+20, U+21, U+23, U+26-29, U+2C-33, U+35-38, U+3A, U+3F, U+41-57, U+61-7A, U+A9, U+E9, U+2013, U+2014, U+2026, U+2AF6, U+1F44B;
      } 
      @font-face {
        font-family: 'Pretendard-Medium';
        font-style: normal;
        font-weight: 500;
        font-display: swap;
        src: url('./fonts/pretendard-medium.woff') format('woff2');
        unicode-range: U+20, U+21, U+23, U+26-29, U+2C-33, U+35-38, U+3A, U+3F, U+41-57, U+61-7A, U+A9, U+E9, U+2013, U+2014, U+2026, U+2AF6, U+1F44B;
      }
      @font-face {
        font-family: 'Pretendard-Regular';
        font-style: normal;
        font-weight: 400;
        font-display: swap;
        src: url('./fonts/pretendard-regular.woff') format('woff2');
        unicode-range: U+20, U+21, U+23, U+26-29, U+2C-33, U+35-38, U+3A, U+3F, U+41-57, U+61-7A, U+A9, U+E9, U+2013, U+2014, U+2026, U+2AF6, U+1F44B;
      }    
      @font-face {
        font-family: 'Fs-Siena-Regular';
        font-style: normal;
        font-weight: 400;
        font-display: swap;
        src: url('./fonts/fs-siena-regular.woff') format('woff2');
        unicode-range: U+20, U+21, U+23, U+26-29, U+2C-33, U+35-38, U+3A, U+3F, U+41-57, U+61-7A, U+A9, U+E9, U+2013, U+2014, U+2026, U+2AF6, U+1F44B;
      }   
        @font-face {
        font-family: 'EastSeaDokdo-Regular';
        font-style: normal;
        font-weight: 400;
        font-display: swap;
        src: url('./fonts/EastSeaDokdo-Regular.ttf') format('truetype');
        unicode-range: U+20, U+21, U+23, U+26-29, U+2C-33, U+35-38, U+3A, U+3F, U+41-57, U+61-7A, U+A9, U+E9, U+2013, U+2014, U+2026, U+2AF6, U+1F44B;
      } 
      `}
    />
  );
};

export default Fonts;
