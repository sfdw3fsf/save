import CustomRangePicker from '@/components/common/CustomRangePicker';
import CustomSelect from '@/components/common/CustomSelect';
import SearchInput from '@/components/common/SearchInput';
import { PostListFilter } from '@/store/post/request/post.request';
import { POST_CATEGORIES } from '@/utils/enums/post/post.enum';
import { helperOption } from '@/utils/helpers/common.helper';
import { orUndefined } from '@/utils/parser/io.parser';
import { DebouncedFunc } from 'lodash';
import { useSearchParams } from 'next/navigation';
import { useTranslation } from 'react-i18next';

type PostTableFilterProps = {
  filter: PostListFilter;
  onFilterChange: DebouncedFunc<(filter: PostListFilter) => void>;
};

const PostTableFilter = ({ filter, onFilterChange }: PostTableFilterProps) => {
  const { t: tEnum } = useTranslation('enum');

  const searchParams = useSearchParams();

  const categoryOptions = helperOption('post.categories', POST_CATEGORIES, tEnum, true);

  const onChangeCategory = (value: POST_CATEGORIES | 'All') => {
    onFilterChange({ filter: JSON.stringify({ ['category.in']: value === 'All' ? '' : [value] }) });
  };

  return (
    <div className='flex flex-1 gap-3 min-h-[38px] flex-wrap  lg:flex-nowrap'>
      <div className='w-[138px]'>
        <CustomSelect options={categoryOptions} defaultValue={filter.categories ?? 'All'} onChange={onChangeCategory} />
      </div>
      <CustomRangePicker
        onChange={(values) => {
          let newValues: [string | number, string | number] = ['', ''];
          if (values[0] && values[1]) {
            newValues = [...(values as [number, number])];
          }
          onFilterChange({ filter: JSON.stringify({ ['createdAt.gte']: newValues[0], ['createdAt.lte']: newValues[1] }) });
        }}
        className=' flex-1 min-w-[150px] lg:flex-none'
      />
      <div className='flex-1 order-3 min-w-[150px] xs:order-none lg:min-w-0'>
        <SearchInput
          defaultValue={orUndefined(searchParams.get('search'))}
          onChange={(e) => {
            onFilterChange({ search: e.target.value });
          }}
        />
      </div>
    </div>
  );
};

export default PostTableFilter;
