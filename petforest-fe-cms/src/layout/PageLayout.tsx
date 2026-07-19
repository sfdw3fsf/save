'use client';
import { useBranch } from '@/hook/branch/useBranch';
import useResize from '@/hook/useResize';
import { COLORS } from '@/theme';
import { Layout } from 'antd';
import { usePathname } from 'next/navigation';
import { ReactNode, useEffect, useState } from 'react';
import BreadCrumb from './breadcumb/BreadCrumb';
import Header from './header/Header';
import SideMenu from './menu/SideMenu';

const { Content } = Layout;

type PageLayoutProps = {
  children: ReactNode;
  isViewMode: boolean;
};

const PageLayout = ({ children, isViewMode = false }: PageLayoutProps) => {
  const { isResponsive } = useResize();
  const [isCollapsed, setIsCollapsed] = useState<boolean>(false);
  const pathName = usePathname();
  const { branchOptions } = useBranch();

  const handleCollapsed = () => {
    setIsCollapsed((pre) => !pre);
  };

  useEffect(() => {
    if (isResponsive) setIsCollapsed(false);
  }, [isResponsive]);

  return (
    <Layout>
      {(!isViewMode || isResponsive) && <SideMenu collapsed={isCollapsed} handleCollapsed={handleCollapsed} />}
      <Layout style={{ background: COLORS.neutral[15] }}>
        {(!isViewMode || isResponsive) && <Header collapsed={isCollapsed} handleCollapsed={handleCollapsed} />}
        <Layout
          style={{
            marginTop: 100,
            marginBottom: 32,
            minHeight: 280,
            backgroundColor: COLORS.neutral[15],
            transition: 'margin-left 0.3s ease',
          }}
          className={`mr-4 lg:mr-10 ml-[16px] ${isViewMode && 'lg:!ml-10 lg:!mt-10 !mb-0'} ${isCollapsed ? 'lg:ml-[120px]' : 'lg:ml-[280px]'}`}>
          {pathName !== '/' && <BreadCrumb />}
          <Content
            style={{
              minHeight: `${isViewMode ? 'calc(100vh - 80px)' : 'calc(100vh - 186px)'} `,
              overflow: 'hidden',
              borderColor: COLORS.neutral[30],
              borderWidth: 1,
              borderRadius: 10,
              backgroundColor: COLORS.white,
              padding: isResponsive ? 12 : 24,
              marginTop: 16,
            }}>
            {children}
          </Content>
        </Layout>
      </Layout>
    </Layout>
  );
};

export default PageLayout;
