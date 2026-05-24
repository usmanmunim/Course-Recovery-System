import os
import re

def remove_comments(content):
    # Remove single-line comments
    content = re.sub(r'//.*?$', '', content, flags=re.MULTILINE)
    # Remove multi-line comments
    content = re.sub(r'/\*.*?\*/', '', content, flags=re.DOTALL)
    # Remove empty lines
    lines = content.split('\n')
    lines = [line for line in lines if line.strip()]
    return '\n'.join(lines)

directories = [
    r'c:\Users\USER\Documents\NetBeansProjects\AssTry\AssTry\src\main\java\crs\models',
    r'c:\Users\USER\Documents\NetBeansProjects\AssTry\AssTry\src\main\java\crs\services',
    r'c:\Users\USER\Documents\NetBeansProjects\AssTry\AssTry\src\main\java\crs\utils'
]

count = 0
for directory in directories:
    if os.path.exists(directory):
        for filename in os.listdir(directory):
            if filename.endswith('.java'):
                filepath = os.path.join(directory, filename)
                with open(filepath, 'r', encoding='utf-8') as f:
                    content = f.read()
                
                cleaned = remove_comments(content)
                
                with open(filepath, 'w', encoding='utf-8') as f:
                    f.write(cleaned)
                
                count += 1
                print(f"Processed: {filename}")

print(f"\nTotal files processed: {count}")
