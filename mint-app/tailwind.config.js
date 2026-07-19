import screens from './src/utils/theme/screens';
import fontSize from './src/utils/theme/fontSize';
import extendColors from './src/utils/theme/extendColors';

/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    screens,
    fontSize,
    extend: {
      colors: {
        ...extendColors,

        'weak-100': '#F6F8FA',
        'soft-400': '#868C98',
        'border-secondary': '#E4E7EC',

        'comp-disabled': '#94A3B8',

        'comp-icon-brand': extendColors.primary[500],
        'comp-icon-tertiary': '#94A3B8',
        'comp-icon-success': extendColors.green[500],
        'comp-icon-secondary': extendColors.slate[500],
        'comp-icon-warning': '#F97316',
        'comp-icon-danger': extendColors.red[500],
        'comp-icon-default': extendColors.slate[800],

        'comp-text-brand': extendColors.primary[500],
        'comp-text-brand-secondary': extendColors.primary[600],
        'comp-text-default': extendColors.slate[800],
        'comp-text-secondary': extendColors.slate[500],
        'comp-text-tertiary': '#94A3B8',
        'comp-text-danger': extendColors.red[500],

        'comp-border-danger-strong': extendColors.red[500],
        'comp-border-neutral-soft': extendColors.slate[200],
        'comp-border-brand-strong': extendColors.primary[500],

        'comp-fill-neutral-reverse': extendColors.slate[800],
      },
      spacing: {
        navbar: '72px',
      },
      height: {
        navbar: '72px',
      },
      boxShadow: {
        'focus-brand': '0 0 0 3px rgba(59, 130, 246, 0.1)',
        'focus-neutral': '0 0 0 3px rgba(148, 163, 184, 0.08)',
        'focus-danger': '0 0 0 3px rgba(239, 68, 68, 0.1)',

        'regular-shadow-x-small': '0 1px 2px 0 rgba(228, 229, 231, 0.24)',

        'elevation-lg': '0 4px 6px -2px rgba(16, 24, 40, 0.03), 0 12px 16px -4px rgba(16, 24, 40, 0.08)',
        'elevation-sm': '0 1px 2px 0px rgba(16, 24, 40, 0.06), 0 1px 3px 0px rgba(16, 24, 40, 0.1)',
      },
      fontSize: {
        xs: ['12px', '18px'],
        sm: ['14px', '20px'],
        md: ['16px', '24px'],
        lg: ['18px', '28px'],
        xl: ['20px', '30px'],
        'display-xs': ['24px', '32px'],
        'display-sm': ['30px', '38px'],
        'display-md': ['36px', '44px'],
        'display-lg': ['48px', '60px'],
        'display-xl': ['60px', '72px'],
      },
    },
  },
  plugins: [],
};
