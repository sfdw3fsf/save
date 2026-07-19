import Avatar from '@/assets/images/Avatar.png';
import FileUploadIcon from '@/components/icons/FileUploadIcon';
import XIcon from '@/components/icons/XIcon';
import { convertMimeTypesToAccept } from '@/utils/helpers/file.helper';
import { ChangeEvent, useMemo, useRef } from 'react';
import { FieldValues, useController, UseControllerProps } from 'react-hook-form';
import { Trans, useTranslation } from 'react-i18next';
import Button from '../../button/Button';

type AvatarType = {
  url?: string;
  key?: string;
  name?: string;
  size?: number;
  type?: string;
};

export type FormAvatarInputProps<T extends FieldValues> = Pick<UseControllerProps<T>, 'name' | 'control'> & {
  mimeTypes?: string[];
  maxSizeInMB?: number;
  showSuccessMessage?: boolean;
  required?: boolean;
};

const FormAvatarInput = <T extends FieldValues>({ name, control, mimeTypes = [] }: FormAvatarInputProps<T>): JSX.Element => {
  const inputRef = useRef<HTMLInputElement>(null);
  const { t: tForm } = useTranslation('form');
  // Define the type for the field value explicitly
  const { field } = useController({
    name,
    control,
  });

  const accept = useMemo(() => convertMimeTypesToAccept(mimeTypes), [mimeTypes]);

  const triggerInput = () => {
    if (inputRef.current) {
      inputRef.current.click();
    }
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files ? e.target.files[0] : null;
    field.onChange(file);
  };

  const handleDelete = () => {
    field.onChange(undefined);
    if (inputRef.current) {
      inputRef.current.value = '';
    }
  };

  const isValidFile = (file: unknown): file is File => {
    return file instanceof File;
  };

  // Simple check to display either file URL or avatar URL
  const getAvatarSrc = () => {
    if (isValidFile(field.value)) {
      return URL.createObjectURL(field.value); // Local file
    } else if ((field.value as AvatarType)?.url) {
      return (field.value as AvatarType).url; // Existing avatar from backend
    }
    return Avatar; // Fallback to default avatar
  };

  return (
    <div className='w-full flex flex-col'>
      <input id={name} type='file' accept={accept} ref={inputRef} hidden onChange={handleChange} />
      <div className='flex flex-row gap-5'>
        <div className='relative'>
          {/* Display the avatar */}
          <img src={getAvatarSrc()} alt='Avatar' className='size-28 rounded-full min-w-24 object-cover' />
          {field.value && (
            <Button
              className='h-[24px] p-0 min-h-6 max-w-6 w-[24px] max-h-5 absolute bottom-8 right-0 rounded-full'
              onClick={handleDelete}>
              <span className='shrink-0 flex items-center justify-center'>
                <XIcon className='w-[10px] h-[10px]' />
              </span>
            </Button>
          )}
        </div>
        <Button className='border-dashed h-[144px] w-[213px] py-4 px-6' sizing='sm' onClick={triggerInput}>
          <div className='flex flex-col gap-3  w-[165px] h-[112px] '>
            <FileUploadIcon className='w-full' />
            <div className='flex flex-col gap-1'>
              <p className='text-center text-xs inline-block'>
                <span className='font-normal'>
                  <Trans
                    i18nKey={'label.avatar_1'}
                    ns='form'
                    values={'as'}
                    components={{ span: <span className='font-medium' /> }}></Trans>
                </span>
              </p>
              <span className='font-medium '>
                {tForm('label.avatar_2')}
                <br></br>
              </span>
              <p className='text-center text-xs font-normal'>
                {tForm('label.avatar_3')}
                <br></br>
                {tForm('label.avatar_4')}
              </p>
            </div>
          </div>
        </Button>
      </div>
    </div>
  );
};

export default FormAvatarInput;
