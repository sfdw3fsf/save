import CustomText from '@/components/common/Text';
import useResize from '@/hook/useResize';
import { NotiHistoryDetailDTO } from '@/store/funeral/noti-history/dto/noti-history.dto';
import Image from 'next/image';
import { useTranslation } from 'react-i18next';

type NotiHistoryDetailModalProps = {
  item: NotiHistoryDetailDTO | null;
};

export const NotiHistoryDetailModal = ({ item }: NotiHistoryDetailModalProps) => {
  const { t: tNotiHistory } = useTranslation('funeral_noti_history');

  const { isResponsive } = useResize();
  const variantText = isResponsive ? 'caption-3' : 'label-2';

  return (
    <div className='flex justify-center items-center flex-col '>
      <div className='mt-4'>
        <CustomText variant='label-1-normal' className='!font-semibold mr-2'>
          {tNotiHistory('recipient_number')}
        </CustomText>
        <CustomText variant='label-1-normal'>{item?.contact}</CustomText>
      </div>
      <div className='relative '>
        <Image src='/images/NotiHistoryFrame.svg' alt='phoneFrame' width={457} height={1024} />
        <div
          className='absolute w-[74.5%] pl-[3%] pr-[2%] py-[3%] bg-white rounded-[14px] whitespace-pre-line'
          style={{ top: '15.6%', left: '16.3%' }}>
          <CustomText variant={variantText}>{item?.content}</CustomText>
        </div>
      </div>
    </div>
  );
};
