import MagnifyingGlassIcon from '@/components/icons/MagnifyingGlassIcon';
type TextInputProps = {
  placeholder: string;
  value?: string;
  onChangeText?: (value: string) => void;
};
const TextInput = ({ placeholder, value, onChangeText }: TextInputProps) => {
  return (
    <div className='border rounded-md flex items-center gap-2 px-3 py-2'>
      <MagnifyingGlassIcon className='text-slate-500' />
      <input
        placeholder={placeholder}
        className='text-sm font-normal text-black placeholder:text-slate-500 w-full'
        onChange={(e) => onChangeText?.(e.target.value)}
        defaultValue={value}
      />
    </div>
  );
};

export default TextInput;
