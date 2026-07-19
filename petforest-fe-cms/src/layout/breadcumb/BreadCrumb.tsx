import { languages } from '@/i18n/i18n.settings';
import { getMenuIcon } from '@/utils/helpers/menu.helper';
import { Breadcrumb } from 'antd';
import { usePathname } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';

const BreadCrumb = () => {
  const pathname = usePathname();

  const { t: tMenu } = useTranslation('menu');

  const pathSnippets = pathname.split('/').filter((item: string) => item && !languages.includes(item));
  const breadcrumbIcon = useMemo(() => {
    return getMenuIcon(pathSnippets);
  }, [pathSnippets]);

  const breadCrumbItems = useMemo(() => {
    const breadCrumbLabels =
      pathSnippets.length === 0
        ? [{ title: tMenu('home') }]
        : pathSnippets
            .map((item: string) => {
              const label = tMenu(item, { defaultValue: '' });
              return label ? { title: label } : null;
            })
            .filter(Boolean);

    return [{ title: breadcrumbIcon }, ...breadCrumbLabels];
  }, [pathSnippets, breadcrumbIcon]);

  return <Breadcrumb separator='>' items={breadCrumbItems.filter((item) => item !== null)} />;
};

export default BreadCrumb;
