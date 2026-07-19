'use client';
import { useTranslation } from 'react-i18next';

import AddButton from '@/components/common/AddButton';
import PageTitle from '@/components/common/page/PageTitle';
import { createStockOrderListApi, stockOrderKeys } from '@/store/stock-order/stock-order.api';
import { handleError } from '@/utils/helpers/notification-error.helper';
import { handleSuccess } from '@/utils/helpers/notification-success.helper';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import StockList from './StockList';

export default function FuneralLucette() {
  const { t: tStock } = useTranslation('funeral_stock');

  const queryClient = useQueryClient();

  const { mutate: mutateListCreate } = useMutation({
    mutationFn: () => createStockOrderListApi(),
    onSuccess: () => {
      handleSuccess('create');
      queryClient.invalidateQueries({ queryKey: stockOrderKeys.lists() });
    },
    onError: handleError,
  });
  const handleListCreate = async () => {
    mutateListCreate();
  };

  return (
    <div>
      <PageTitle title={tStock('title')} actions={<AddButton onClick={handleListCreate} />} />

      <div className='mt-4'>
        <StockList />
      </div>
    </div>
  );
}
