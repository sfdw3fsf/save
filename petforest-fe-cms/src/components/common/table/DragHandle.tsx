import { ICONS } from '@/theme';
import { Button } from 'antd';
import Image from 'next/image';
import React, { useContext } from 'react';
import RowContext from './RowContext';

const DragHandle: React.FC = () => {
  const { setActivatorNodeRef, listeners } = useContext(RowContext);

  return (
    <Button
      type='text'
      size='small'
      icon={<Image src={ICONS.DRAG} alt='Drag' width={24} height={24} />}
      style={{ cursor: 'move' }}
      ref={setActivatorNodeRef}
      {...listeners}
    />
  );
};

export default DragHandle;
