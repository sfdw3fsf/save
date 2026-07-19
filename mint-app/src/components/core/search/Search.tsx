import MagnifyingGlassIcon from '@/components/icons/MagnifyingGlassIcon';
import { Control } from 'react-hook-form';
import { twMerge } from 'tailwind-merge';
import FormField from '../form/form-field/FormField';

type SearchProps = {
  className?: string;
  control: Control;
  placeholder?: string;
  searchValue: string;
  onSearch: (value: string) => void;
  rightSection?: React.ReactNode;
  bottomSection?: React.ReactNode;
};
const Search = ({
  className,
  control,
  placeholder,
  searchValue,
  onSearch,
  rightSection,
  bottomSection,
}: SearchProps): JSX.Element => {
  return (
    <div className={twMerge('flex flex-col', className)}>
      <div className='flex flex-row items-center gap-4'>
        <div className='md:max-w-[652px] w-full'>
          <FormField
            className='gap-0'
            type='input'
            componentProps={{
              control,
              name: 'search',
              placeholder,
              leftInputNode: <MagnifyingGlassIcon className='text-slate-500' />,
              value: searchValue,
              onChange: (e) => {
                onSearch(e.target.value);
              },
            }}
          />
        </div>
        {!!rightSection && rightSection}
      </div>
      {!!bottomSection && bottomSection}
    </div>
  );
};

export default Search;
