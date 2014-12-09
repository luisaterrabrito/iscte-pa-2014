package pa.iscde.dropcode.dropreflection;

import org.eclipse.jdt.core.dom.Modifier;

public class DropModifier {

	public enum DM_Visibility {
		PUBLIC {
			@Override
			public boolean isPresent(int mod) {
				return Modifier.isPublic(mod);
			}
		},
		PROTECTED {
			@Override
			public boolean isPresent(int mod) {
				return Modifier.isProtected(mod);
			}
		},
		PRIVATE {
			@Override
			public boolean isPresent(int mod) {
				return Modifier.isPrivate(mod);
			}
		},
		PACKAGE_PRIVATE {
			@Override
			public boolean isPresent(int mod) {
				return !Modifier.isPrivate(mod) && !Modifier.isPublic(mod)
						&& !Modifier.isProtected(mod);
			}
		};

		public abstract boolean isPresent(int mod);
	}

	public enum DM_Others {
		FINAL {
			@Override
			public boolean isPresent(int mod) {
				return Modifier.isFinal(mod);
			}
		},
		STATIC {
			@Override
			public boolean isPresent(int mod) {
				return Modifier.isStatic(mod);
			}
		},
		ABSTRACT {
			@Override
			public boolean isPresent(int mod) {
				return Modifier.isAbstract(mod);
			}
		};
		public abstract boolean isPresent(int mod);

	}
}
