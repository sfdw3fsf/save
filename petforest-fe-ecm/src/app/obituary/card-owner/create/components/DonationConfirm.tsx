'use client';
import { ObituaryDTO } from '@/store/obituary/dto/obituary.dto';
import { QuestionIcon } from '@chakra-ui/icons';
import { Card, CardBody, Checkbox } from '@chakra-ui/react';
import { Controller, useFormContext } from 'react-hook-form';

type DonationConfirmProps = {
  onDonationQuestion: () => void;
};
const DonationConfirm = ({ onDonationQuestion }: DonationConfirmProps) => {
  const { control } = useFormContext<ObituaryDTO>();

  return (
    <Card className='w-full mt-[25px] px-[15px] py-[30px] text-[14px] font-medium rounded-[18px]'>
      <CardBody className='flex items-center gap-2'>
        <span className='text-green-main text-[20px] font-bold'>조의금 전달 버튼</span>
        <button onClick={onDonationQuestion} className='flex items-center justify-center '>
          <QuestionIcon color={'#0d4221'} w={6} h={6} />
        </button>
        <Controller
          name='isPaymentActivated'
          control={control}
          render={({ field: { onChange, value } }) => {
            return (
              <Checkbox
                size={'lg'}
                isChecked={value}
                onChange={onChange}
                _checked={{
                  '& .chakra-checkbox__control': { background: '#0d4221', borderColor: '#0d4221' },
                  '& .chakra-checkbox__control:hover': { background: '#0d4221', borderColor: '#0d4221' },
                }}
                iconColor='white'
              />
            );
          }}
        />
      </CardBody>
    </Card>
  );
};

export default DonationConfirm;
