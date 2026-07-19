import { SelectProps, Tooltip } from 'antd';
import CustomSelect from './CustomSelect';

const CustomMultiSelect = ({ ...props }: Omit<SelectProps, 'maxTagCount' | 'maxTagPlaceholder'>) => {
  return (
    <CustomSelect
      showSearch={true}
      allowClear
      mode='multiple'
      maxTagCount='responsive'
      maxTagPlaceholder={(omittedValues: any) => (
        <Tooltip overlayStyle={{ pointerEvents: 'none' }} title={omittedValues.map(({ label }: any) => label).join(', ')}>
          <span>+ {omittedValues.length}...</span>
        </Tooltip>
      )}
      {...props}
    />
  );
};

export default CustomMultiSelect;
