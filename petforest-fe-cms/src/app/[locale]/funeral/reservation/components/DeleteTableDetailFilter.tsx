import CustomRangePicker from '@/components/common/CustomRangePicker';
import CustomSelect from '@/components/common/CustomSelect';
import SearchInput from '@/components/common/SearchInput';

const DeleteTableDetailFilter = () => {
  return (
    <div className='flex flex-1 gap-3 min-h-[38px] flex-wrap lg:flex-nowrap'>
      <div className='flex min-w-[150px] flex-wrap w-full gap-3 lg:w-auto  md:flex-1 order-2 lg:order-1'>
        <div className='flex-1 min-w-[150px]'>
          <SearchInput />
        </div>
        <CustomSelect placeholder='동물종류' />
      </div>
      <div className='flex flex-1 min-w-[150px] w-full  '>
        <CustomRangePicker classNameLabel='flex-1 grow' />
      </div>
    </div>
  );
};

export default DeleteTableDetailFilter;
