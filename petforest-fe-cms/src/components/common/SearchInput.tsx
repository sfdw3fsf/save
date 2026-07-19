import { COLORS } from '@/theme';
import { SearchOutlined } from '@ant-design/icons';
import { useTranslation } from 'react-i18next';
import { CustomTextInput, CustomTextInputProps } from './CustomTextInput';

const SearchInput = ({ ...inputProps }: CustomTextInputProps) => {
  const { t: tCommon } = useTranslation('common');
  return (
    <CustomTextInput
      {...inputProps}
      placeholder={tCommon('place_holder.search')}
      prefix={<SearchOutlined style={{ color: COLORS.neutral[60] }} />}
    />
  );
};

export default SearchInput;
