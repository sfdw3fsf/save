const defaultTheme = {
  toast: {
    main: 'text-comp-text-default',
    sub: 'text-comp-text-secondary',
    base: 'bg-white border border-comp-border-neutral-soft shadow-elevation-lg w-full max-w-[390px]',
  },
};

export const theme = {
  color: {
    main: {
      error: 'text-red-600',
      ['toast-error']: defaultTheme.toast.main,
      ['toast-success']: defaultTheme.toast.main,
      ['toast-info']: defaultTheme.toast.main,
    },
    sub: {
      error: 'text-red-500',
      ['toast-error']: defaultTheme.toast.sub,
      ['toast-success']: defaultTheme.toast.sub,
      ['toast-info']: defaultTheme.toast.sub,
    },
    icon: {
      error: 'text-red-500',
      ['toast-error']: 'text-comp-icon-danger',
      ['toast-success']: 'text-comp-icon-success',
      ['toast-info']: 'text-cerulean-600',
    },
  },
  base: {
    error: 'bg-red-50 border-red-300',
    ['toast-error']: defaultTheme.toast.base,
    ['toast-success']: defaultTheme.toast.base,
    ['toast-info']: defaultTheme.toast.base,
  },
};
