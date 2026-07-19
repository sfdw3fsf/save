'use client';
import PageTitle from '@/components/common/page/PageTitle';
import { FUNERAL_LUCETTE_TABS } from '@/utils/enums/funeral/lucette-mngt.enums';
import { Tabs, TabsProps } from 'antd';
import { useRouter, useSearchParams } from 'next/navigation';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import LucetteList from './Tabs/LucetteList';
import { LucetteSchedule } from './Tabs/LucetteSchedule';

export default function FuneralLucette() {
  const { t: tLucette } = useTranslation('funeral_lucette');

  const searchParams = useSearchParams();
  const router = useRouter();

  const tabActiveKey = useMemo<FUNERAL_LUCETTE_TABS>(() => {
    const tabParam = searchParams.get('tab');
    switch (tabParam) {
      case FUNERAL_LUCETTE_TABS.SCHEDULE:
        return tabParam;
      default:
        return FUNERAL_LUCETTE_TABS.LIST;
    }
  }, [searchParams]);

  const tabItems: TabsProps['items'] = [
    {
      key: FUNERAL_LUCETTE_TABS.LIST,
      label: tLucette('lucette.title'),
      children: <LucetteList />,
    },
    {
      key: FUNERAL_LUCETTE_TABS.SCHEDULE,
      label: tLucette('lucette_schedule.title'),
      children: <LucetteSchedule />,
    },
  ];

  const onTabChange = (key: string) => {
    const newUrl = new URL(window.location.href);
    newUrl.searchParams.set('tab', key);
    router.push(newUrl.toString());
  };

  return (
    <div>
      <PageTitle title={tLucette('funeral_lucette')} />

      <div className='mt-4'>
        <Tabs items={tabItems} activeKey={tabActiveKey} onChange={onTabChange} className='custom-tab' />
      </div>
    </div>
  );
}
