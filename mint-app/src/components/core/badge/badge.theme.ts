const defaultTheme = {
  color: 'text-comp-text-secondary border-slate-200 bg-white',
};

export const theme = {
  shape: {
    'square-round': 'rounded-md',
    round: 'rounded-full',
  },
  color: {
    text: {
      green: 'text-primary-500 bg-primary-50 border-primary-200',
      blue: 'text-cerulean-700 bg-cerulean-50 border-cerulean-200',
      orange: 'text-[#F97316] bg-[#FFEDD5] border-[#FDBA74]',
      red: 'text-red-500 bg-red-100 border-red-300',
      neutral: 'text-slate-500 bg-slate-50 border-slate-200',
    },
    status: {
      green: defaultTheme.color,
      blue: defaultTheme.color,
      orange: defaultTheme.color,
      red: defaultTheme.color,
      neutral: defaultTheme.color,
    },
  },
  icon: {
    green: 'text-comp-icon-success',
    blue: 'text-cerulean-700',
    orange: 'text-comp-icon-warning',
    red: 'text-comp-icon-danger',
    neutral: 'text-comp-icon-secondary',
  },
};
