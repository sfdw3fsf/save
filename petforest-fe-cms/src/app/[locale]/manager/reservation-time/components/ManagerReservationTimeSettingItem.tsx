import CustomButton from '@/components/common/CustomButton';
import { COLORS } from '@/theme';
import { CheckOutlined } from '@ant-design/icons';

type ManagerReservationTimeSettingItemProps = {
  title: string;
  onClick: () => void;
  isActive?: boolean;
};

const ManagerReservationTimeSettingItem = ({ title, onClick, isActive }: ManagerReservationTimeSettingItemProps) => {
  return (
    <CustomButton onClick={onClick} type={isActive ? 'ghost' : 'default'} className='!h-[44px] !w-[89px] text-[16px] font-medium'>
      {isActive && <CheckOutlined color={COLORS.primary[1000]} />}
      {title}
    </CustomButton>
  );
};

export default ManagerReservationTimeSettingItem;
