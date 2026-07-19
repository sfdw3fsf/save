import TrashIcon from '@/components/icons/TrashIcon';
import UploadIcon from '@/components/icons/UploadIcon';
import { convertMimeTypesToAccept } from '@/utils/helpers/file.helper';
import { ChangeEvent, useMemo, useRef } from 'react';
import { FieldValues, useController, UseControllerProps } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import Button from '../../button/Button';
import FileCard from '../../file-card/FileCard';

export type FormFileInputProps<T extends FieldValues> = Pick<UseControllerProps<T>, 'name' | 'control'> & {
  mimeTypes?: string[];
  maxSizeInMB?: number;
  showSuccessMessage?: boolean;
  required?: boolean;
  maxFile?: number;
};

const FormFileInput = <T extends FieldValues>({
  name,
  control,
  mimeTypes = [],
  maxSizeInMB,
  maxFile = 1,
}: FormFileInputProps<T>): JSX.Element => {
  const { t: tCommon } = useTranslation('common');

  const inputRef = useRef<HTMLInputElement>(null);

  const { field } = useController({ name, control });

  const accept = useMemo(() => convertMimeTypesToAccept(mimeTypes), [mimeTypes]);
  const isMultiple = useMemo<boolean>(() => maxFile > 1, [maxFile]);

  const triggerInput = () => {
    if (inputRef.current) {
      inputRef.current.click();
    }
  };

  const getRenamedFile = (originalFile: File, index: number) => {
    const newName = `${originalFile.name.replace(/\.[^.]+$/, '')} (${index})${originalFile.name.match(/\.[^.]+$/)}`;
    return new File([originalFile], newName, { type: originalFile.type });
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files ? e.target.files[0] : null;

    if (!isMultiple) {
      field.onChange(file);
    } else if (file) {
      const newFiles = [...field.value] as File[];

      let renamedFile = file;
      let index = 1;

      while (newFiles.some((i) => i.name === renamedFile.name)) {
        renamedFile = getRenamedFile(file, index);
        index++;
      }

      newFiles.push(renamedFile);
      field.onChange(newFiles);
    }

    if (inputRef.current) {
      inputRef.current.value = '';
    }
  };

  const handleDelete = (index?: number) => {
    if (!isMultiple) {
      field.onChange(undefined);
      return;
    }

    if (index === undefined) return;

    const newFiles = [...field.value];
    newFiles.splice(index, 1);
    field.onChange(newFiles);
  };

  const hideButton = useMemo<boolean>(() => {
    if (!field.value) return false;
    return isMultiple ? field.value.length === maxFile : !!field.value;
  }, [field.value, isMultiple, maxFile]);

  return (
    <div className='w-full'>
      <input id={name} type='file' accept={accept} ref={inputRef} hidden onChange={handleChange} />
      <div className='flex flex-col gap-2'>
        {isMultiple ? (
          (field.value as File[])?.map((item, index) => (
            <FileCard
              key={`${index}-${item.name}`}
              file={item}
              buttonProps={{ icon: <TrashIcon />, variant: 'greyGhost', sizing: 'xs', onClick: () => handleDelete(index) }}
            />
          ))
        ) : (
          <FileCard
            file={field.value}
            buttonProps={{ icon: <TrashIcon />, variant: 'greyGhost', sizing: 'xs', onClick: () => handleDelete() }}
          />
        )}

        {!hideButton && (
          <Button className='w-fit' sizing='sm' leftIcon={<UploadIcon />} onClick={triggerInput}>
            {tCommon('component.file_upload')}
          </Button>
        )}
      </div>
    </div>
  );
};

export default FormFileInput;
