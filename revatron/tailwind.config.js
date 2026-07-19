// import { extendColors } from './src/utils/theme/extendColors'
/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        // extendColors: extendColors
        primary: {
          '500': '#3AAD4B',
          '600': '#2B8E3B',
          '700': '#257031',
          '58': '#2EA658',
          '3': '#E1F7E3',
        },
        warm: {
          '8': '#F7F8F8',
          '7': ' #F7F7F7',
          '12': '#121212',
          '25': '#FDFDFC',
          '100': '#F5F5F4',
          '200': '#E7E5E4',
          '300': '#D7D3D0',
          '400': '#A9A29D',
          '500': '#79716B',
          '600': '#57534E',
          '700': '#44403C',
          '800': '#292524',
          '950': '#171412',
        },
        feat: {
          '30': '#F2AE30'
        },
        text: {
          '36': '#363636'
        },
        border: {
          '9': '#E9EAEB'
        }


      }
    },
  },
  plugins: [],
}

