import FileIcon from '@/components/icons/FileIcon';
import { convertFileSizeToString, getFileExtension } from '@/utils/helpers/file.helper';
import { ReactNode } from 'react';
import IconButton, { IconButtonProps } from '../icon-button/IconButton';

type FileCardProps = {
  file?: File | null;
  rightNode?: ReactNode;
  buttonProps?: IconButtonProps;
};

const FileCard = ({ file, rightNode, buttonProps }: FileCardProps): JSX.Element => {
  if (!file) return <></>;

  return (
    <div className='flex w-full items-center justify-between gap-3 p-3 rounded-lg border border-comp-border-neutral-soft bg-white'>
      <div className='flex w-full h-10 items-center justify-start gap-3'>
        <div className='flex h-full items-center justify-center shrink-0'>
          <FileIcon variant={getFileExtension(file.name)} />
        </div>

        <div className='flex w-full flex-col gap-1 font-normal text-left truncate'>
          <p title={file.name} className='text-sm text-comp-text-default truncate'>
            {/* {file.name} */}
            {file.name.length > 27 ? file.name.slice(0, 28) + '...' : file.name}
          </p>
          <p className='text-xs text-comp-text-secondary'>{convertFileSizeToString(file.size)}</p>
        </div>
      </div>

      {!!(rightNode || buttonProps) && (
        <div className='flex w-fit h-full items-center justify-end gap-3'>
          {!!buttonProps && <IconButton iconClassName='text-comp-icon-secondary' {...buttonProps} />}
          {rightNode}
        </div>
      )}
    </div>
  );
};

export default FileCard;
