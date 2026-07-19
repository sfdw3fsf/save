import CustomText from '@/components/common/Text';
import { useTranslation } from 'react-i18next';

type PNTSettingProps = {
  onClick?: () => void;
  displayName?: string;
  index: number;
  petName?: string;
  guardianName?: string;
  selectedImage?: string;
};

const PNTSetting = ({ onClick, displayName = '', petName, guardianName, selectedImage = '', index }: PNTSettingProps) => {
  const { t: tEnum } = useTranslation('enum');

  const background = selectedImage !== '' ? `url(${selectedImage})` : 'white';
  return (
    <div
      onClick={onClick}
      className={`relative border border-neutral-40 h-[118px] rounded-[10px] overflow-hidden ${onClick ? 'cursor-pointer' : ''} `}
      style={{
        background: background,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
      }}>
      <div className='relative h-full'>
        <div className='absolute inset-0 bg-black opacity-60 z-0'></div>
        <div className='absolute inset-0 z-10 flex flex-col justify-center items-center'>
          <CustomText variant='label-1-normal' className='!text-white'>
            {tEnum(`reservation.room_type.${displayName}`, displayName)}
          </CustomText>
          <CustomText variant='headline-1' className='!text-white'>
            {petName}
          </CustomText>
          <CustomText variant='label-1-normal' className='!text-white'>
            {guardianName}
          </CustomText>
        </div>
      </div>
    </div>
  );
};

export default PNTSetting;
