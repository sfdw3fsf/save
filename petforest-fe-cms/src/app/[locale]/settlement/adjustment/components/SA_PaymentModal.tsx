import CenteredModal from '@/components/common/CenteredModal';
import CustomFormInput from '@/components/common/form/CustomFormField';
import { SettlementAdjustmentListDTO } from '@/store/settlement/dto/settlement.dto';
import { InputEnum } from '@/utils/enums/input.enum';
import { orUndefined } from '@/utils/parser/io.parser';
import { settlementMethodSchema, SettlementMethodType } from '@/utils/schemas/settlement/settlement.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

type SA_PaymentModalProps = {
  isOpen: boolean;
  onClose: () => void;
  title?: string;
  onSubmit: (data: SettlementMethodType) => void;
  isLoading?: boolean;
  defaultValues?: SettlementAdjustmentListDTO;
};

const SA_PaymentModal = ({ isOpen, onClose, onSubmit, isLoading, defaultValues }: SA_PaymentModalProps) => {
  const { t: tSettlement } = useTranslation('settlement');

  const { control, handleSubmit, reset } = useForm<SettlementMethodType>({
    defaultValues: settlementMethodSchema.getDefault(),
    resolver: yupResolver<SettlementMethodType>(settlementMethodSchema),
  });

  useEffect(() => {
    reset({
      cash: orUndefined(defaultValues?.settlementMethod?.cash),
      accountAmount: orUndefined(defaultValues?.settlementMethod?.accountAmount),
      card: orUndefined(defaultValues?.settlementMethod?.card),
    });
  }, [defaultValues]);

  return (
    <CenteredModal
      isOpen={isOpen}
      onCancel={onClose}
      loading={isLoading}
      onOk={handleSubmit(onSubmit)}
      title={tSettlement('table.settlement_method_and_amount')}>
      <div className='my-4 flex flex-col gap-6'>
        <div>
          <CustomFormInput
            control={control}
            name='card'
            type={InputEnum.THOUSAND_NUMBER}
            label={tSettlement('payment_method.card')}
            placeholder={tSettlement('payment_method.place_holder.card')}
          />
        </div>
        <div>
          <CustomFormInput
            control={control}
            name='cash'
            type={InputEnum.THOUSAND_NUMBER}
            label={tSettlement('payment_method.cash')}
            placeholder={tSettlement('payment_method.place_holder.cash')}
          />
        </div>
        <div>
          <CustomFormInput
            control={control}
            name='accountAmount'
            type={InputEnum.THOUSAND_NUMBER}
            label={tSettlement('payment_method.account')}
            placeholder={tSettlement('payment_method.place_holder.account')}
          />
        </div>
      </div>
    </CenteredModal>
  );
};

export default SA_PaymentModal;
