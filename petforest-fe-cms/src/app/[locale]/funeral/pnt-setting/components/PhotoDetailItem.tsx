import { Checkbox } from 'antd';

type PhotoItemProps = {
  item: string;
  index: number;
  onClickCheckbox: (index: number) => void;
  checked: boolean;
};

export const PhotoItem = ({ item, index, onClickCheckbox, checked }: PhotoItemProps) => {
  return (
    <div className='flex items-center flex-col cursor-pointer gap-[8px]' onClick={() => onClickCheckbox(index)}>
      <div className='relative w-full aspect-square'>
        <img src={item} alt={`${index}`} className='absolute inset-0 w-full h-full object-cover' />
      </div>
      <Checkbox checked={checked}></Checkbox>
    </div>
  );
};
