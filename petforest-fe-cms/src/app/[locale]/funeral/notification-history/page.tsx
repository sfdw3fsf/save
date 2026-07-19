'use client';
import { useTranslation } from 'react-i18next';

import PageTitle from '@/components/common/page/PageTitle';
import NotiHistoryList from './NotiHistoryList';

export default function Home() {
  const { t: tNotiHistory } = useTranslation('funeral_noti_history');

  return (
    <div>
      <PageTitle title={tNotiHistory('noti_history')} />

      <div className='mt-4'>
        <NotiHistoryList />
      </div>
    </div>
  );
}
