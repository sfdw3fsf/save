const defaultTheme = {
  spacing: {
    normal: {
      '2sm': 'rounded-[4px] py-1 px-[7px] gap-1',
      sm: 'rounded-lg py-2 px-[12px] gap-1',
      md: 'rounded-lg py-2.5 px-[14px] gap-1',
      lg: 'rounded-lg py-2.5 px-[16px] gap-1.5',
      xl: 'rounded-lg py-3 px-[18px] gap-1.5',
    },
    dense: {
      '2sm': 'gap-1',
      sm: 'gap-1',
      md: 'gap-1',
      lg: 'gap-1.5',
      xl: 'gap-1.5',
    },
  },
};

export const theme = {
  base: {
    buttonCont: 'group relative flex items-center justify-center font-medium text-center',
    fontSize: {
      '2sm': 'text-sm',
      sm: 'text-sm',
      md: 'text-sm',
      lg: 'text-md',
      xl: 'text-md',
    },
    cont: 'flex items-center justify-center gap-1',
    content: 'px-0.5 flex items-center justify-center truncate',
    iconContainer: 'flex items-center justify-center shrink-0',
    icon: {
      '2sm': 'h-4 w-4',
      sm: 'h-4 w-4',
      md: 'h-5 w-5',
      lg: 'h-5 w-5',
      xl: 'h-5 w-5',
    },
    controlHeight: {
      '2sm': 'min-h-7',
      sm: 'min-h-9',
      md: 'min-h-10',
      lg: 'min-h-11',
      xl: 'min-h-12',
    },
  },
  variant: {
    primary: {
      color: 'text-white group-disabled:text-comp-disabled',
      base: 'bg-primary-500 enabled:hover:bg-primary-600 enabled:active:bg-primary-700 enabled:hover:shadow-focus-brand enabled:active:shadow-focus-brand disabled:border-slate-200 disabled:border disabled:bg-slate-100',
      spacing: defaultTheme.spacing.normal,
      border: 'border-white border-b-transparent group-disabled:border-comp-disabled group-disabled:border-b-transparent',
    },
    secondaryGray: {
      color: 'text-slate-500 group-disabled:text-comp-disabled group-hover:text-slate-800 group-active:text-slate-800',
      base: 'bg-white border hover:border-slate-500 active:border-slate-500 enabled:hover:shadow-focus-neutral enabled:active:shadow-focus-neutral disabled:border-slate-200 disabled:bg-slate-100',
      spacing: defaultTheme.spacing.normal,
      border:
        'border-slate-500 border-b-transparent group-disabled:border-comp-disabled group-disabled:border-b-transparent group-hover:border-slate-800 group-hover:border-b-transparent group-active:border-slate-800 group-active:border-b-transparent',
    },
    linkColor: {
      color: 'text-primary-500 group-disabled:text-comp-disabled group-hover:text-primary-600 group-active:text-primary-500',
      base: 'bg-transparent',
      spacing: defaultTheme.spacing.dense,
      border:
        'border-primary-500 border-b-transparent group-disabled:border-comp-disabled group-disabled:border-b-transparent group-hover:border-primary-600 group-hover:border-b-transparent group-active:border-primary-500 group-active:border-b-transparent',
    },
    link: {
      color: 'text-slate-500 group-disabled:text-comp-disabled group-hover:text-slate-800 group-active:text-slate-500',
      base: 'bg-transparent',
      spacing: defaultTheme.spacing.dense,
      border:
        'border-slate-500 border-b-transparent group-disabled:border-comp-disabled group-disabled:border-b-transparent group-hover:border-slate-800 group-hover:border-b-transparent group-active:border-slate-500 group-active:border-b-transparent',
    },
    grayBox: {
      color: 'text-slate-500 group-disabled:text-comp-disabled group-hover:text-slate-800 group-active:text-slate-500',
      base: 'bg-transparent',
      spacing: defaultTheme.spacing.normal,
      border:
        'border-slate-500 border-b-transparent group-disabled:border-comp-disabled group-disabled:border-b-transparent group-hover:border-slate-800 group-hover:border-b-transparent group-active:border-slate-500 group-active:border-b-transparent',
    },
  },
};
