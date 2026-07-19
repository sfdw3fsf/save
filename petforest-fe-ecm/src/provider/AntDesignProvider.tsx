import { ConfigProvider } from 'antd';
import locale from 'antd/locale/ko_KR';
import dayjs from 'dayjs';
import 'dayjs/locale/ko';
import { ReactNode } from 'react';

function AntDesignProvider({ children }: { children: ReactNode }) {
  dayjs.locale('ko');

  return (
    <ConfigProvider
      theme={{
        token: {
          // Seed Token
          colorPrimary: '#1c472c',
          borderRadius: 2,
        },
      }}
      locale={locale}>
      {children}
    </ConfigProvider>
  );
}

export default AntDesignProvider;
