import Button from '@/components/core/button/Button';
import Search from '@/components/core/search/Search';
import FunnelIcon from '@/components/icons/FunnelIcon';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';

type SearchAndFilterProps = {
  searchValue: string;
  onSearch: (value: string) => void;
  onFilter?: () => void;
  leftButton?: React.ReactNode;
};
const SearchAndFilter = ({ searchValue, leftButton, onSearch, onFilter }: SearchAndFilterProps): JSX.Element => {
  const { t: tCommon } = useTranslation('common');
  const { control } = useForm();
  return (
    <div className='flex flex-col md:flex-row w-full justify-between items-center my-6 gap-4'>
      <div className='md:max-w-[452px] w-full'>
        <Search
          control={control}
          searchValue={searchValue}
          onSearch={onSearch}
          placeholder={tCommon('project_management.search_placeholder')}
          rightSection={
            <>
              {onFilter ? (
                <Button
                  className='h-12'
                  keepLeftIconStyle={true}
                  leftIcon={<FunnelIcon className='mr-2 text-slate-500' />}
                  onClick={onFilter}>
                  {tCommon('project_management.filter')}
                </Button>
              ) : null}
            </>
          }
        />
      </div>
      {leftButton && leftButton}
    </div>
  );
};

export default SearchAndFilter;
