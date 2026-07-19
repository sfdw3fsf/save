'use client';
import { AntdRegistry } from '@ant-design/nextjs-registry';
import { ConfigProvider } from 'antd';
import ko_KR from 'antd/locale/ko_KR';
import dayjs from 'dayjs';
import 'dayjs/locale/ko';
import { ReactNode, useEffect } from 'react';
import AntdThemeConfig from '../theme/antd.theme';

const AntDesignProvider = ({ children }: { children: ReactNode }) => {
  useEffect(() => {
    dayjs.locale('ko');
  }, []);

  return (
    <AntdRegistry>
      <ConfigProvider locale={ko_KR} theme={AntdThemeConfig}>
        {children}
      </ConfigProvider>
    </AntdRegistry>
  );
};

export default AntDesignProvider;
