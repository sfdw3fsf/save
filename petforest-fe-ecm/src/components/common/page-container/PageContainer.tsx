'use client';
import { usePathname } from 'next/navigation';
import { ReactNode, useMemo } from 'react';

type PageContainerProps = {
  children: ReactNode;
  className?: string;
};

const PageContainer = ({ children, className }: PageContainerProps) => {
  const pathname = usePathname();

  const isInHome = useMemo(() => {
    return pathname === '/';
  }, [pathname]);

  return <div className={`cont ${isInHome ? 'lg:!mt-[105px]' : 'md:mt-[72px]'}  ${className ? className : ''}`}>{children}</div>;
};

export default PageContainer;
