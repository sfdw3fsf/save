import Dropdown, { DropdownItem } from '@/components/core/dropdown/Dropdown';
import FormField from '@/components/core/form/form-field/FormField';
import CaretDownIcon from '@/components/icons/CaretDownIcon';
import MagnifyingGlassIcon from '@/components/icons/MagnifyingGlassIcon';
import { useDebounce } from '@uidotdev/usehooks';
import { useEffect, useState } from 'react';
import { Control } from 'react-hook-form';
import { Trans, useTranslation } from 'react-i18next';
import { useSearchParams } from 'react-router-dom';
import Button from '../button/Button';

type FaqSearchFilterProps = {
  control: Control;
  dropdownItems: DropdownItem[];
  localeName?: string;
};

const SearchSection = ({ control, dropdownItems, localeName }: FaqSearchFilterProps): JSX.Element => {
  const { t: tFaq } = useTranslation('faq');
  const [searchParams, setSearchParams] = useSearchParams();
  const [searchInput, setSearchInput] = useState('');
  const debouncedSearchInput = useDebounce(searchInput, 300);
  const count = searchParams.get('totalElements');

  const [dropdownName, setDropdownName] = useState('entire');
  useEffect(() => {
    if (searchInput.length === 0 || searchInput.length <= 2) {
      searchParams.set('search', searchInput);
      setSearchParams(searchParams, { replace: true });
    } else if (debouncedSearchInput.length > 2) {
      searchParams.set('search', debouncedSearchInput);
      setSearchParams(searchParams, { replace: true });
    }
    // For cases already in page 3 but when filter or search only have 2 pages
    searchParams.set('pageNo', '1');
  }, [debouncedSearchInput, searchInput, searchParams, setSearchParams]);

  //Dropdown change category
  const handleSelect = (value: any) => {
    if (value === 'entire') {
      searchParams.delete('category');
      // setDropdownName(tFaq('entire'));
    } else {
      searchParams.set('category', value);
    }
    setDropdownName(value);

    searchParams.set('pageNo', '1');
    setSearchParams(searchParams, { replace: true });
  };

  const translatedDropdownName =
    dropdownName === 'entire'
      ? tFaq('entire')
      : tFaq(`${localeName}.${dropdownName.toLowerCase()}` as any).includes('.')
        ? dropdownName // If translation key contains ".", fallback to the original dropdownName
        : tFaq(`${localeName}.${dropdownName.toLowerCase()}` as any);

  const displayName = translatedDropdownName.length > 3 ? `${translatedDropdownName.substring(0, 3)}...` : translatedDropdownName;
  return (
    <div className='flex flex-col pb-4'>
      <div className='flex gap-1.5 flex-row  max-w-[768px] w-full'>
        <div className='md:max-w-[666px] w-full inline-block '>
          <FormField
            className='gap-0'
            type='input'
            componentProps={{
              sizing: 'lg',
              control,
              name: 'searchQuery',
              placeholder: tFaq('searchPlaceholder'),
              leftInputNode: <MagnifyingGlassIcon className='text-comp-text-tertiary w-[16px] h-[16px]' />,
              value: searchInput,
              onChange: (e) => setSearchInput(e.target.value),
            }}
          />
        </div>

        <div className='max-w-[96px] w-full max-h-28 h-full'>
          <Dropdown
            items={dropdownItems}
            onSelect={handleSelect}
            className='border border-slate-200  rounded-md text-sx h-full w-full max-w-[96px] '>
            <Button
              className=' border-none text-base text-slate-50  w-full  max-h-28 h-12 '
              // leftIcon={}
              sizing='lg'
              rightIcon={<CaretDownIcon className='' />}
              keepLeftIconStyle>
              {displayName}
            </Button>
          </Dropdown>
        </div>
      </div>

      <div className='text-lg font-medium text-slate-500 pt-1.5 '>
        {debouncedSearchInput === '' ? (
          <Trans
            i18nKey={'total'}
            ns='faq'
            values={{ count }}
            components={{ span: <span className='text-primary-500 font-bold' /> }}
          />
        ) : (
          <Trans
            i18nKey={'have_result'}
            ns='faq'
            values={{ count, term: debouncedSearchInput }}
            components={{ span: <span className='text-primary-500 font-bold' /> }}
          />
        )}
      </div>
    </div>
  );
};

export default SearchSection;
