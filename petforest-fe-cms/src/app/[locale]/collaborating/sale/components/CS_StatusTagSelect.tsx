import Status from '@/components/common/Status';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { SelectProps } from 'antd';
import { useTranslation } from 'react-i18next';

type TagRender = SelectProps['tagRender'];

export const StatusTagRender: TagRender = (props) => {
  const { t: tEnum } = useTranslation('enum');
  const { value } = props;
  return <Status isSuccess={value === ENUM_STATUS.COMPLETE} status={tEnum(`status.${value}` as any)} />;
};
