import ReactQuill, { ReactQuillProps } from 'react-quill';

type CustomReactQuillInputProps = ReactQuillProps;
export const CustomReactQuillInput = ({ ...props }: CustomReactQuillInputProps) => {
  return (
    <ReactQuill
      theme='snow'
      style={{ height: '308px' }}
      modules={{
        toolbar: [
          [{ header: [1, 2, false] }],
          ['bold', 'italic', 'underline', 'strike', 'blockquote'],
          [{ list: 'ordered' }, { list: 'bullet' }, { indent: '-1' }, { indent: '+1' }],
          ['link', 'image'],
          ['clean'],
        ],
      }}
      {...props}
    />
  );
};
