import React from 'react';

import { Resizable as RawResizable, ResizeCallbackData } from 'react-resizable';
import 'react-resizable/css/styles.css';
import './styles/resizeable-title.css';

const Resizable = RawResizable as unknown as React.ComponentType<ResizableTitleProps>;

interface ResizableTitleProps {
  onResize: (e: React.SyntheticEvent<Element>, data: ResizeCallbackData) => void;
  width: number | undefined;
  [key: string]: any;
}

export const ResizableTitle: React.FC<ResizableTitleProps> = (props) => {
  const { onResize, width, ...restProps } = props;

  if (!width) {
    return <th {...restProps} />;
  }

  return (
    <Resizable
      width={width}
      height={0}
      handle={<span className='react-resizable-handle' onClick={(e) => e.stopPropagation()} />}
      onResize={onResize}
      draggableOpts={{ enableUserSelectHack: false }}>
      <th {...restProps} />
    </Resizable>
  );
};
