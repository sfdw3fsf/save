import extendColors from './src/utils/theme/extendColors'
import screens from './src/utils/theme/screens'
/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      screens,
      colors: {
        ...extendColors
      },
      spacing: {
        navbar: '88px',
      },
      height: {
        navbar: '88px'
      }
    },
  },
  plugins: [],
}

