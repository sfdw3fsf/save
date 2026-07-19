import CustomDatePicker from '@/components/common/CustomDatePicker';
import dayjs, { Dayjs } from 'dayjs';

export const CHS_ChangeDateModal = ({ onChangeDate }: { onChangeDate: (date: Dayjs | null) => void }) => {
  return (
    <div className='flex flex-col py-4 gap-6 w-full'>
      <CustomDatePicker onChange={onChangeDate} defaultValue={dayjs().startOf('day')} />
    </div>
  );
};
