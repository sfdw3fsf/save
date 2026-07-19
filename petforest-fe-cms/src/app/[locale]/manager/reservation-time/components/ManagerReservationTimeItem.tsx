import CustomButton from '@/components/common/CustomButton';
import { RESERVATION_TIME_STATUS } from '@/utils/enums/manager/manager.enum';

type ManagerReservationTimeItemProps = {
  title: string;
  onClick: () => void;

  status: RESERVATION_TIME_STATUS;
};

const ManagerReservationTimeItem = ({ title, onClick, status }: ManagerReservationTimeItemProps) => {
  if (status === RESERVATION_TIME_STATUS.OPENING) {
    return (
      <CustomButton onClick={onClick} type='default' className={`!h-[40px] !w-[86px]`}>
        {title}
      </CustomButton>
    );
  }

  if (status === RESERVATION_TIME_STATUS.CLOSED) {
    return (
      <CustomButton
        onClick={onClick}
        type='default'
        className={`!h-[40px] !w-[86px] !bg-neutral-30 !text-neutral-60 !border-none`}>
        {title}
      </CustomButton>
    );
  }

  if (status === RESERVATION_TIME_STATUS.COMPLETED) {
    return (
      <CustomButton
        onClick={onClick}
        type='default'
        className={`!h-[40px] !w-[86px] !bg-brand-forest-1 !text-primary-900 !border-none`}>
        {title}
      </CustomButton>
    );
  }
};

export default ManagerReservationTimeItem;
