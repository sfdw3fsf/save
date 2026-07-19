import { COLORS } from '@/theme';
import type { ThemeConfig } from 'antd';

const AntdThemeConfig: ThemeConfig = {
  token: {
    fontFamily: 'Pretendard-Regular',
    colorPrimary: COLORS.primary[900],
    colorInfo: COLORS.primary[900],
  },
  components: {
    Button: {
      primaryShadow: 'none',
      paddingInline: 16,
      fontSize: 14,
      fontWeight: 500,
      lineHeight: 20,
      defaultBorderColor: COLORS.neutral[40],
      colorText: COLORS.neutral[80],
      colorPrimary: COLORS.green.main,
    },
    Layout: {
      headerBg: COLORS.white,
    },
    Menu: {
      itemColor: COLORS.neutral[100],
      itemSelectedColor: COLORS.green['deep-teal'],
      fontSize: 14,
      itemSelectedBg: COLORS.green['ivory'],
      itemHoverBg: COLORS.green['ivory'],
    },
    Breadcrumb: {
      itemColor: COLORS.grey.dim,
      linkColor: COLORS.grey.dim,
      lastItemColor: COLORS.grey.dim,
    },
    Segmented: {
      itemHoverBg: 'transparent',
      trackBg: 'transparent',
      colorBorder: 'transparent',
      boxShadow: 'none',
      itemSelectedColor: COLORS.primary[850],
      itemColor: COLORS.neutral[70],
      controlHeight: 40,
    },
    Table: {
      rowSelectedBg: COLORS.primary[100],
    },
    Tabs: {
      inkBarColor: COLORS.black,
      itemSelectedColor: COLORS.black,
      itemColor: COLORS.neutral[60],
      itemHoverColor: COLORS.black,
    },
    Cascader: {
      optionSelectedBg: COLORS.primary[50],
    },
  },
};

export default AntdThemeConfig;
